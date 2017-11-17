package org.molgenis.downloader.client;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HttpClientFactory
{

	public static HttpClient create(final boolean insecure) throws KeyManagementException, NoSuchAlgorithmException
	{
		HttpClient client;
		if (insecure)
		{
			client = createInsecureHttpClient();
		}
		else
		{
			client = HttpClients.createDefault();
		}
		return client;
	}

	private static HttpClient createInsecureHttpClient() throws KeyManagementException, NoSuchAlgorithmException
	{
		SSLContext sslContext = SSLContext.getInstance("SSL");
		// set up a TrustManager that trusts everything
		sslContext.init(null, new TrustManager[] { new X509TrustManager()
		{
			@Override
			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType)
			{
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType)
			{
			}
		} }, new SecureRandom());

		return HttpClients.custom()
						  .setSSLContext(sslContext)
						  .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
						  .build();
	}

}
