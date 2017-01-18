/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.client.HttpClientFactory;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.emx.EMXClient;
import org.molgenis.downloader.client.MolgenisRestApiClient;

/**
 *
 * @author david
 */
public class Downloader {

    private final Path outFile;
    private List<String> entities;
    private URI url;
    private boolean includeMetaData;
    private boolean insecureSSL;
    private String username;
    private String password;

    public static void main(final String[] args) {
        try {
            final Downloader app = new Downloader(args);
            app.run();
        } catch (final Exception ex) {
            System.console().format("An error occurred: %s\n",
                    ex.getLocalizedMessage()).flush();
        }
    }

    private Downloader(final String[] args) throws ParseException, URISyntaxException {
        final DefaultParser parser = new DefaultParser();
        final Options options = createCmdLineOptions();
        try {
            final CommandLine parseResult = parser.parse(options, args);
            entities = parseResult.getArgList();
            outFile = Paths.get(parseResult.getOptionValue("o"));
            url = new URI(parseResult.getOptionValue("u"));
            includeMetaData = parseResult.hasOption("m");
            username = parseResult.getOptionValue("U");
            password = parseResult.getOptionValue("P");
            insecureSSL = parseResult.hasOption("I");

        } catch (final ParseException | URISyntaxException ex) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar downloader.jar [options] [entity1 [entity2 [entity3] …]]", options);
            throw ex;
        }
    }

    private Options createCmdLineOptions() {
        final Options options = new Options();
        final Option outFileOption = Option.builder("o")
                .argName("Output file").hasArg().longOpt("outFile")
                .desc("Name of the file to write the data to.").required().build();
        final Option urlOption = Option.builder("u")
                .argName("URL").hasArg().longOpt("url")
                .desc("URL of the MOLGENIS instance").required().build();
        final Option metaOption = Option.builder("m")
                .argName("Write metadata").longOpt("meta")
                .desc("Write the metadata for the entities to the output file.").build();
        final Option userOption = Option.builder("U")
                .argName("Username").hasArg().longOpt("user")
                .desc("MOLGENIS username to login with to download the data.").build();
        final Option passwordOption = Option.builder("P")
                .argName("Password").hasArg().longOpt("password")
                .desc("Password for the MOLGENIS user to login").build();
        final Option insecureSSLOption = Option.builder("I")
                .argName("Ignore SSL errors").longOpt("insecureSSL")
                .desc("Ignore SSL certicate chain errors and hostname mismatches.").build();
        options.addOption(outFileOption)
                .addOption(urlOption)
                .addOption(metaOption)
                .addOption(userOption)
                .addOption(passwordOption)
                .addOption(insecureSSLOption);
        return options;
    }

    private void run() throws Exception {
        final HttpClient client = HttpClientFactory.create(insecureSSL);

        try (final MolgenisClient molgenis = new MolgenisRestApiClient(client, url)) {

            if (username != null) {
                if (password == null) {
                    System.console().writer().append("Password: ").flush();
                    password = String.copyValueOf(System.console().readPassword());
                }
                molgenis.login(username, password);
            }
            try (final EMXClient emx = new EMXClient(molgenis)) {
                boolean hasErrors = emx.downloadEMX(entities, outFile, includeMetaData);
                if (hasErrors) {
                    System.console().format("Errors occurred while writing EMX\n").flush();
                    emx.getErrors().forEach(ex -> System.console().format("Exception: %s\n", ex.getLocalizedMessage()).flush());
                }
            }
        }
    }
}
