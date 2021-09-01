package org.molgenis.downloader.integration;

import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_2;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.client.MolgenisRestApiClient;
import org.molgenis.downloader.rdf.RdfClient;
import org.molgenis.downloader.rdf.RdfConfig;
import org.molgenis.downloader.rdf.RdfConfigImpl;
import org.testng.annotations.Test;

public class RdfClientTest {
  @Test
  public void rdfITTest() throws Exception {
    HttpClient httpClient = new TestHttpClient();
    MolgenisClient molgenisClient = new MolgenisRestApiClient(httpClient, new URI(""));
    RdfConfig rdfConfig = new RdfConfigImpl();
    RdfClient rdfClient = new RdfClient(molgenisClient, rdfConfig);

    File outputFile = File.createTempFile("download", ".ttl");
    rdfClient.export(
        outputFile, Collections.singletonList("org_molgenis_test_TypeTest"), 100, VERSION_2);

    String actual = FileUtils.readFileToString(outputFile, "utf-8");
    String expected =
        IOUtils.toString(getClass().getResourceAsStream("/integration/export.ttl"), "utf-8");
    assertEquals(actual, expected);
  }
}
