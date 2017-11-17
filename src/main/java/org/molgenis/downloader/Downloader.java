package org.molgenis.downloader;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.client.HttpClientFactory;
import org.molgenis.downloader.client.MolgenisRestApiClient;
import org.molgenis.downloader.emx.EMXClient;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static org.molgenis.downloader.util.ConsoleWriter.writeHelp;
import static org.molgenis.downloader.util.ConsoleWriter.writeToConsole;

public class Downloader
{
	private static final String URL = "url";
	private static final String DATA_ONLY = "dataOnly";
	private static final String ACCOUNT = "account";
	private static final String PASSWORD = "password";
	private static final String INSECURE_SSL = "insecureSSL";
	private static final String ARGUMENTS = "[arguments]";
	private static final String FILE = "outputFile";
	private static final String OVERWRITE = "overwrite";
	private static final String PAGESIZE = "pageSize";
	private static final String DEBUG = "debug";
	private static final String VERSION = "version";
	private static final String SOCKET_TIMEOUT = "timeout";
	private static final Integer DEFAULT_SOCKET_TIMEOUT = 60;

	public static boolean debug;

	public static void main(final String[] args)
	{
		try
		{
			final Downloader app = new Downloader();
			OptionParser parser = createOptionParser();
			OptionSet options;
			try
			{
				options = parser.parse(args);
				app.run(options);
			}
			catch (OptionException ex)
			{
				writeToConsole("An error occurred:", ex);
				writeHelp(parser);
			}
		}
		catch (final Exception ex)
		{
			writeToConsole("An error occurred:", ex);
		}
	}

	private static OptionParser createOptionParser()
	{
		OptionParser parser = new OptionParser();
		parser.acceptsAll(asList("f", FILE), "Name of the file to write the data to.")
			  .withRequiredArg()
			  .ofType(File.class)
			  .required();
		parser.acceptsAll(asList("o", OVERWRITE), "Overwrite the exisiting file if it exists.");
		parser.acceptsAll(asList("u", URL), "URL of the MOLGENIS instance")
			  .withRequiredArg()
			  .ofType(String.class)
			  .required();
		parser.acceptsAll(asList("D", DATA_ONLY), "Write only the data for the entities to the output file.");
		parser.acceptsAll(asList("a", ACCOUNT), "MOLGENIS username to login with to download the data.")
			  .withRequiredArg()
			  .ofType(String.class);
		parser.acceptsAll(asList("p", PASSWORD), "Password for the MOLGENIS user to login")
			  .withRequiredArg()
			  .ofType(String.class);
		parser.acceptsAll(asList("i", INSECURE_SSL), "Ignore SSL certicate chain errors and hostname mismatches.");
		parser.acceptsAll(asList("s", PAGESIZE),
				"The pagesize for the REST responses, increase in case of large datasets, maximum value=10000")
			  .withRequiredArg()
			  .ofType(Integer.class);
		parser.acceptsAll(asList("d", DEBUG), "print debug logging to console");
		parser.acceptsAll(asList("v", VERSION), "Optional parameter to override the result form '/api/v2/version/'")
			  .withRequiredArg()
			  .ofType(String.class);
		parser.acceptsAll(asList("t", SOCKET_TIMEOUT),
				"Optional parameter to configure the socket timeout in seconds, default value is 60")
			  .withRequiredArg()
			  .ofType(Integer.class);

		return parser;
	}

	private void run(OptionSet options) throws Exception
	{
		File outFile = (File) options.valueOf(FILE);
		@SuppressWarnings("unchecked")
		List<String> entities = (List<String>) options.valuesOf(ARGUMENTS);
		URI url = options.hasArgument(URL) ? new URI((String) options.valueOf(URL)) : null;
		Integer pageSize = options.hasArgument(PAGESIZE) ? (Integer) options.valueOf(PAGESIZE) : null;
		boolean includeMetaData = !options.has(DATA_ONLY);
		boolean insecureSSL = options.has(INSECURE_SSL);
		String username = (String) options.valueOf(ACCOUNT);
		String password = (String) options.valueOf(PASSWORD);
		boolean overwrite = options.has(OVERWRITE);
		String versionString = (String) options.valueOf(VERSION);
		Integer socketTimeout = options.hasArgument(SOCKET_TIMEOUT) ? (Integer) options.valueOf(
				SOCKET_TIMEOUT) : DEFAULT_SOCKET_TIMEOUT;

		debug = options.has(DEBUG);

		final HttpClient client = HttpClientFactory.create(insecureSSL);

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
				molgenis.login(username, password, socketTimeout);
			}
			try (final EMXClient emxClient = new EMXClient(molgenis))
			{
				MolgenisVersion version;
				if (versionString != null)
				{
					version = MolgenisVersion.from(versionString);
				}
				else
				{
					version = molgenis.getVersion();
				}

				boolean hasErrors = emxClient.downloadEMX(entities, Paths.get(outFile.getPath()), includeMetaData,
						overwrite, version, pageSize);
				if (hasErrors)
				{
					writeToConsole("Errors occurred while writing EMX\n");
					emxClient.getExceptions().forEach(ex -> writeToConsole("Exception: %s\n", ex));
				}
			}
		}
	}
}
