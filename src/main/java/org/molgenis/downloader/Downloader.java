/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.molgenis.downloader.client.HttpClientFactory;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.emx.EMXClient;
import org.molgenis.downloader.client.MolgenisRestApiClient;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import static java.util.Arrays.asList;
import static org.molgenis.downloader.util.ConsoleWriter.writeToConsole;

/**
 * @author david
 */
public class Downloader
{

	public static final String OUT_FILE = "outFile";
	public static final String URL = "url";
	public static final String META = "meta";
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String INSECURE_SSL = "insecureSSL";
	public static final String ARGUMENTS = "[arguments]";

	public static void main(final String[] args)
	{
		try
		{
			final Downloader app = new Downloader();
			OptionParser parser = createOptionParser();
			OptionSet options = parser.parse(args);
			app.run(options, parser);
		}
		catch (final Exception ex)
		{
			writeToConsole("An error occurred: %s\n", ex);
		}
	}

	private static OptionParser createOptionParser()
	{
		OptionParser parser = new OptionParser();
		parser.acceptsAll(asList("o", OUT_FILE), "Name of the file to write the data to.").withRequiredArg()
				.ofType(File.class);
		parser.acceptsAll(asList("u", URL), "URL of the MOLGENIS instance").withRequiredArg().ofType(String.class);
		parser.acceptsAll(asList("m", META), "Write the metadata for the entities to the output file.");
		parser.acceptsAll(asList("a", ACCOUNT), "MOLGENIS username to login with to download the data.").withRequiredArg()
				.ofType(String.class);
		parser.acceptsAll(asList("p", PASSWORD), "Password for the MOLGENIS user to login").withRequiredArg().ofType(String.class);
		parser.acceptsAll(asList("i", INSECURE_SSL), "Ignore SSL certicate chain errors and hostname mismatches.");

		return parser;
	}

	private void run(OptionSet options, OptionParser parser) throws Exception
	{
		File outFile = (File) options.valueOf(OUT_FILE);
		List<String> entities = (List<String>) options.valuesOf(ARGUMENTS);
		URI url = options.hasArgument(URL) ? new URI((String) options.valueOf(URL)) : null;
		boolean includeMetaData = options.has(META);
		boolean insecureSSL = options.has(INSECURE_SSL);
		String username = (String) options.valueOf(ACCOUNT);
		String password = (String) options.valueOf(PASSWORD);

		final HttpClient client = HttpClientFactory.create(insecureSSL);

		//TODO: check for arguments

		try (final MolgenisClient molgenis = new MolgenisRestApiClient(client, url))
		{

			if (username != null)
			{
				if (password == null)
				{
					if (System.console() != null)
					{
						System.console().writer().append("Password: ").flush();
						password = String.copyValueOf(System.console().readPassword());
					}
					else
					{
						System.out.println(
								"An empty password is not possible for running the downloader in a 'consoleless' environment.");
						return;
					}
				}
				molgenis.login(username, password);
			}
			try (final EMXClient emx = new EMXClient(molgenis))
			{
				boolean hasErrors = emx.downloadEMX(entities, Paths.get(outFile.getPath()), includeMetaData);
				if (hasErrors)
				{
					writeToConsole("Errors occurred while writing EMX\n");
					emx.getErrors().forEach(ex -> writeToConsole("Exception: %s\n", ex));
				}
			}
		}
	}
}
