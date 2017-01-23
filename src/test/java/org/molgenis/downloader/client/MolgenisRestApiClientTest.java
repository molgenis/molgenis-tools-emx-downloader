package org.molgenis.downloader.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

import com.google.common.io.Resources;

public class MolgenisRestApiClientTest
{
	@Test
	public void versionTest() throws IOException, URISyntaxException
	{
		HttpClient httpClient = mock(HttpClient.class);
		HttpResponse httpResponse = mock(HttpResponse.class);
		HttpEntity httpEntity = mock(HttpEntity.class);

		File resource = new File(Resources.getResource("versionResponse.txt").getPath());
		when(httpEntity.getContent()).thenReturn(new FileInputStream(resource));
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpClient.execute(any())).thenReturn(httpResponse);

		MolgenisRestApiClient client = new MolgenisRestApiClient(httpClient, new URI(""));
		MolgenisVersion version = client.getVersion();
		MolgenisVersion expected = new MolgenisVersion(9, 9, 9);
		assertEquals(version, expected);
	}
}
