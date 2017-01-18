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

import joptsimple.*;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.client.HttpClientFactory;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.emx.EMXClient;
import org.molgenis.downloader.client.MolgenisRestApiClient;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import static java.util.Arrays.asList;

/**
 * @author david
 */
public class Downloader
{

	public static final String OUT_FILE = "outFile";
	public static final String URL = "url";
	public static final String META = "meta";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String INSECURE_SSL = "insecureSSL";

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
			System.console().format("An error occurred: %s\n", ex.getLocalizedMessage()).flush();
		}
	}

	private static OptionParser createOptionParser()
	{
		OptionParser parser = new OptionParser();
		parser.acceptsAll(asList("o", OUT_FILE), "Name of the file to write the data to.").withRequiredArg()
				.ofType(File.class);
		parser.acceptsAll(asList("u", URL), "URL of the MOLGENIS instance").withRequiredArg().ofType(String.class);
		parser.acceptsAll(asList("m", META), "Write the metadata for the entities to the output file.");
		parser.acceptsAll(asList("U", USER), "MOLGENIS username to login with to download the data.").withRequiredArg()
				.ofType(String.class);
		parser.acceptsAll(asList("P", PASSWORD), "Password for the MOLGENIS user to login");
		parser.acceptsAll(asList("I", INSECURE_SSL), "Ignore SSL certicate chain errors and hostname mismatches.");

		return parser;
	}

	private void run(OptionSet options, OptionParser parser) throws Exception
	{
		OptionSpec<String> entitiesOption = parser.nonOptions().ofType(String.class);

		File outFile = (File) options.valueOf(OUT_FILE);
		List<String> entities = options.valuesOf(entitiesOption);
		URI url = options.hasArgument(URL) ? new URI((String) options.valueOf(URL)) : null;
		boolean includeMetaData = options.has(META);
		boolean insecureSSL = options.has(INSECURE_SSL);
		String username = (String) options.valueOf(USER);
		String password = (String) options.valueOf(PASSWORD);

		final HttpClient client = HttpClientFactory.create(insecureSSL);

		//TODO: check for arguments

		try (final MolgenisClient molgenis = new MolgenisRestApiClient(client, url))
		{

			if (username != null)
			{
				if (password == null)
				{
					System.console().writer().append("Password: ").flush();
					password = String.copyValueOf(System.console().readPassword());
				}
				molgenis.login(username, password);
			}
			try (final EMXClient emx = new EMXClient(molgenis))
			{
				boolean hasErrors = emx.downloadEMX(entities, Paths.get(outFile.getPath()), includeMetaData);
				if (hasErrors)
				{
					System.console().format("Errors occurred while writing EMX\n").flush();
					emx.getErrors().forEach(
							ex -> System.console().format("Exception: %s\n", ex.getLocalizedMessage()).flush());
				}
			}
		}
	}
}
