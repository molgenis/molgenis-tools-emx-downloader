package org.molgenis.downloader.integration;

import com.google.common.io.Resources;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.client.MolgenisRestApiClient;
import org.molgenis.downloader.emx.EMXClient;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

public class EmxClientTest
{
	@Test
	public void zipITTest() throws Exception
	{
		HttpClient httpClient = new TestHttpClient();
		MolgenisRestApiClient client = new MolgenisRestApiClient(httpClient, new URI(""));
		EMXClient emxClient = new EMXClient(client);

		File actual = File.createTempFile("download", ".zip");
		emxClient.downloadEMX(Collections.singletonList("org_molgenis_test_TypeTest"), actual.toPath(), true, true, null);
		File expected = new File(Resources.getResource("integration/download.zip").getPath());
		ZipFileAssert.assertEquals(expected, actual);
	}
}
