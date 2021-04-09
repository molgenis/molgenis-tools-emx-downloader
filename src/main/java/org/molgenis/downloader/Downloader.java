package org.molgenis.downloader;

import ch.qos.logback.classic.Level;
import com.google.common.base.Charsets;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.client.HttpClientFactory;
import org.molgenis.downloader.client.MolgenisRestApiClient;
import org.molgenis.downloader.emx.EMXClient;
import org.molgenis.downloader.rdf.RdfClient;
import org.molgenis.downloader.rdf.RdfConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.base.Strings.repeat;
import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.readLines;
import static java.util.Arrays.asList;

public class Downloader
{
	private static final Logger LOG = LoggerFactory.getLogger(Downloader.class);
	private static final String URL = "url";
	private static final String DATA_ONLY = "dataOnly";
	private static final String ACCOUNT = "account";
	@SuppressWarnings("squid:S2068")
	private static final String PASSWORD = "password";
	private static final String INSECURE_SSL = "insecureSSL";
	private static final String ARGUMENTS = "[arguments]";
	private static final String FILE = "outputFile";
	private static final String OVERWRITE = "overwrite";
	private static final String PAGESIZE = "pageSize";
	private static final String DEBUG = "debug";
	private static final String VERSION = "version";
	private static final String SOCKET_TIMEOUT = "timeout";
	private static final String DEFAULT_NAMESPACE = "defaultNamespace";
	private static final String NAMESPACES = "namespaces";
	private static final Integer DEFAULT_SOCKET_TIMEOUT = 60;
	private static final String RDF = "rdf";

	public static void main(final String[] args) throws IOException
	{
		printBanner();
		try
		{
			OptionSet options = tryParseOptions(args);
			new Downloader().run(options);
		}
		catch (OptionException ex)
		{
			LOG.info(ex.getMessage());
			LOG.info("Examples: \n"
					+ "'java -jar downloader.jar -f output.xls -a username -u molgenisserver.nl entity1 entity2 entity3'\n"
					+ "'java -jar downloader.jar -f export.ttl -a username -u molgenisserver.nl --rdf entity1 entity2 entity3'\n");
		}
		catch (Exception ex)
		{
			LOG.error("An error occurred:", ex);
		}
	}

	private static void printBanner() throws IOException
	{
		String implementationVersion = Downloader.class.getPackage().getImplementationVersion();
		System.out.println(repeat("-", 110));
		for (String line : readLines(getResource("banner.txt"), Charsets.UTF_8))
		{
			System.out.println(line);
		}
		System.out.println("Version: " + (implementationVersion != null ? implementationVersion : "DEVELOPMENT"));
		System.out.println(repeat("-", 110));
		System.out.println();
	}

	private static OptionSet tryParseOptions(String[] args)
	{
		OptionParser parser = createOptionParser();
		return parser.parse(args);
	}

	private static OptionParser createOptionParser()
	{
		OptionParser parser = new OptionParser();
		parser.acceptsAll(asList("f", FILE), "Name of the file to write the data to.")
			  .withRequiredArg()
			  .ofType(File.class)
			  .required();
		parser.acceptsAll(asList("o", OVERWRITE), "Overwrite the file if it exists.");
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
		parser.acceptsAll(asList("v", VERSION), "Overrides the result from '/api/v2/version'")
			  .withRequiredArg()
			  .ofType(String.class);
		parser.acceptsAll(asList("t", SOCKET_TIMEOUT), "The socket timeout in seconds, default value is 60")
			  .withRequiredArg()
			  .ofType(Integer.class);
		parser.accepts(DEFAULT_NAMESPACE,
				"The default namespace for newly created IRIs in RDF download, and the prefix to use. "
						+ "Format is prefix:namespace. " + "Default value is 'mlg:http://molgenis.org/'.")
			  .withRequiredArg()
			  .ofType(String.class);
		parser.accepts(NAMESPACES, "A properties file containing namespace prefixes to add to the defaults.")
			  .withRequiredArg()
			  .ofType(File.class);
		parser.accepts(RDF,
				"Specifies that the output should be in RDF format instead of EMX. Implies that only data gets exported.");

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

		if (options.has(DEBUG))
		{
			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(
					"org.molgenis");
			root.setLevel(Level.DEBUG);
		}

		final HttpClient client = HttpClientFactory.create(insecureSSL);

		try (final MolgenisClient molgenis = new MolgenisRestApiClient(client, url))
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
			logOptionInfo(outFile, url, pageSize, includeMetaData, insecureSSL, username, overwrite, socketTimeout,
					version);
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
						LOG.error(
								"An empty password is not possible for running the downloader in a 'consoleless' environment.");
						return;
					}
				}
				molgenis.login(username, password, socketTimeout);
			}

			if (outFile.exists())
			{
				if (overwrite)
				{
					if (!outFile.delete())
					{
						LOG.error("Failed to overwrite existing output file. Aborting export.");
						return;
					}
					LOG.info("Deleted existing output file.");
				}
				else
				{
					LOG.error("Output file already exists and overwrite options is not specified. Aborting export.");
					return;
				}
			}

			if (options.has(RDF))
			{
				RdfConfigImpl rdfConfig = new RdfConfigImpl();
				if (options.has(NAMESPACES))
				{
					LOG.info("Loading additional namespaces from {}", options.valueOf(NAMESPACES));
					rdfConfig.loadAdditionalNamespaces((File) options.valueOf(NAMESPACES));
				}
				if (options.has(DEFAULT_NAMESPACE))
				{
					String defaultNamespaceOption = (String) options.valueOf(DEFAULT_NAMESPACE);
					LOG.info("Setting default namespace to {}...", defaultNamespaceOption);
					String[] parts = defaultNamespaceOption.split(":", 2);
					if (parts.length != 2 || !parts[1].contains(":"))
					{
						LOG.error("Error parsing default namespace option '{}'.\n"
								+ "Make sure that you provide a prefix, followed by a :, followed by a valid URI.\n"
								+ "For example: '--defaultNamespace ex:http://example.com/'", defaultNamespaceOption);
						return;
					}
					LOG.info("Using default namespace {} with prefix {}", parts[1], parts[0]);
					rdfConfig.setDefaultNamespace(parts[0], parts[1]);
				}
				new RdfClient(molgenis, rdfConfig).export(outFile, entities, pageSize, version);
			}
			else
			{
				final EMXClient emxClient = new EMXClient(molgenis);
				boolean hasErrors = emxClient.downloadEMX(entities, Paths.get(outFile.getPath()), includeMetaData,
						overwrite, version, pageSize);
				if (hasErrors)
				{
					LOG.warn("Errors occurred while writing EMX\n");
					emxClient.getExceptions().forEach(ex -> LOG.warn("Error: ", ex));
				}
			}
		}

	}

	private void logOptionInfo(File outFile, URI url, Integer pageSize, boolean includeMetaData, boolean insecureSSL,
			String username, boolean overwrite, Integer socketTimeout, MolgenisVersion version)
	{
		if (LOG.isInfoEnabled())
		{
			LOG.info("Options:");
			LOG.info("outFile:       {}", outFile);
			LOG.info("url:           {}", url);
			LOG.info("account:       {}", username);
			LOG.info("socketTimeout: {}", socketTimeout);
			if (version != null) {
				LOG.info("version:       {}", version.toVersionString());
			}
			if (pageSize != null) LOG.info("pageSize:      {}", pageSize);
			if (!includeMetaData) LOG.info("* only data");
			if (insecureSSL) LOG.info("* insecure SSL");
			if (overwrite) LOG.info("* overwrite existing output if present");
		}
	}

}
