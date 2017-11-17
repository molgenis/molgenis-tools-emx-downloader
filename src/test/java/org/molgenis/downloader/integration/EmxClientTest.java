package org.molgenis.downloader.integration;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.client.MolgenisRestApiClient;
import org.molgenis.downloader.emx.EMXClient;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Collections;

import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_2;

public class EmxClientTest
{
	@Test
	public void zipITTest() throws Exception
	{
		HttpClient httpClient = new TestHttpClient();
		MolgenisRestApiClient client = new MolgenisRestApiClient(httpClient, new URI(""));
		EMXClient emxClient = new EMXClient(client);

		File actual = File.createTempFile("download", ".zip");
		emxClient.downloadEMX(Collections.singletonList("org_molgenis_test_TypeTest"), actual.toPath(), true, true,
				VERSION_2, null);

		File expected = File.createTempFile("download-expected", ".zip");
		FileOutputStream outputStream = new FileOutputStream(expected);
		IOUtils.copy(getClass().getResourceAsStream("/integration/download.zip"), outputStream);
		outputStream.close();

		ZipFileAssert.assertEquals(expected, actual);
	}
}
