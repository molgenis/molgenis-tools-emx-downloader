package org.molgenis.downloader.integration;

import com.google.common.io.Resources;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHttpClient implements HttpClient
{
	@Override
	public HttpParams getParams()
	{
		throw new NotImplementedException();
	}

	@Override
	public ClientConnectionManager getConnectionManager()
	{
		throw new NotImplementedException();
	}

	@Override
	public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException
	{


		HttpResponse versionHttpResponse = mock(HttpResponse.class);
		HttpEntity versionHttpEntity = mock(HttpEntity.class);
		when(versionHttpEntity.getContent()).thenReturn(new FileInputStream(new File(
				Resources.getResource("integration/version.txt").getPath())));
		when(versionHttpResponse.getEntity()).thenReturn(versionHttpEntity);

		HttpResponse languageHttpResponse = mock(HttpResponse.class);
		HttpEntity languageHttpEntity = mock(HttpEntity.class);
		when(languageHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/language.txt").getPath())));
		when(languageHttpResponse.getEntity()).thenReturn(languageHttpEntity);

		HttpResponse tagHttpResponse = mock(HttpResponse.class);
		HttpEntity tagHttpEntity = mock(HttpEntity.class);
		when(tagHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/tags.txt").getPath())));
		when(tagHttpResponse.getEntity()).thenReturn(tagHttpEntity);

		HttpResponse packageHttpResponse = mock(HttpResponse.class);
		HttpEntity packageHttpEntity = mock(HttpEntity.class);
		when(packageHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/package.txt").getPath())));
		when(packageHttpResponse.getEntity()).thenReturn(packageHttpEntity);

		HttpResponse attributeHttpResponse = mock(HttpResponse.class);
		HttpEntity attributeHttpEntity = mock(HttpEntity.class);
		when(attributeHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/attributes.txt").getPath())));
		when(attributeHttpResponse.getEntity()).thenReturn(attributeHttpEntity);

		HttpResponse attribute100HttpResponse = mock(HttpResponse.class);
		HttpEntity attribute100HttpEntity = mock(HttpEntity.class);
		when(attribute100HttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/attributes100.txt").getPath())));
		when(attribute100HttpResponse.getEntity()).thenReturn(attribute100HttpEntity);

		HttpResponse attribute200HttpResponse = mock(HttpResponse.class);
		HttpEntity attribute200HttpEntity = mock(HttpEntity.class);
		when(attribute200HttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/attributes200.txt").getPath())));
		when(attribute200HttpResponse.getEntity()).thenReturn(attribute200HttpEntity);

		HttpResponse attribute300HttpResponse = mock(HttpResponse.class);
		HttpEntity attribute300HttpEntity = mock(HttpEntity.class);
		when(attribute300HttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/attributes300.txt").getPath())));
		when(attribute300HttpResponse.getEntity()).thenReturn(attribute300HttpEntity);

		HttpResponse attribute400HttpResponse = mock(HttpResponse.class);
		HttpEntity attribute400HttpEntity = mock(HttpEntity.class);
		when(attribute400HttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/attributes400.txt").getPath())));
		when(attribute400HttpResponse.getEntity()).thenReturn(attribute400HttpEntity);

		HttpResponse entityHttpResponse = mock(HttpResponse.class);
		HttpEntity entityHttpEntity = mock(HttpEntity.class);
		when(entityHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/type.txt").getPath())));
		when(entityHttpResponse.getEntity()).thenReturn(entityHttpEntity);

		HttpResponse dataHttpResponse = mock(HttpResponse.class);
		HttpEntity dataHttpEntity = mock(HttpEntity.class);
		when(dataHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/data.txt").getPath())));
		when(dataHttpResponse.getEntity()).thenReturn(dataHttpEntity);

		HttpResponse locationHttpResponse = mock(HttpResponse.class);
		HttpEntity locationHttpEntity = mock(HttpEntity.class);
		when(locationHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/location.txt").getPath())));
		when(locationHttpResponse.getEntity()).thenReturn(locationHttpEntity);

		HttpResponse refHttpResponse = mock(HttpResponse.class);
		HttpEntity refHttpEntity = mock(HttpEntity.class);
		when(refHttpEntity.getContent()).thenReturn(new FileInputStream(new File(Resources.getResource("integration/ref.txt").getPath())));
		when(refHttpResponse.getEntity()).thenReturn(refHttpEntity);

		HttpResponse response = null;

		if(httpUriRequest.getURI().getPath().equals("/api/v2/version"))
			response = versionHttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/sys_Language"))
			response = languageHttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/sys_md_Tag"))
			response = tagHttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/sys_md_Package"))
			response = packageHttpResponse;
		else if(httpUriRequest.getURI().getSchemeSpecificPart().equals("/api/v2/sys_md_Attribute"))
			response = attributeHttpResponse;
		else if(httpUriRequest.getURI().getSchemeSpecificPart().equals("/api/v2/sys_md_Attribute?start=100"))
			response = attribute100HttpResponse;
		else if(httpUriRequest.getURI().getSchemeSpecificPart().equals("/api/v2/sys_md_Attribute?start=200"))
			response = attribute200HttpResponse;
		else if(httpUriRequest.getURI().getSchemeSpecificPart().equals("/api/v2/sys_md_Attribute?start=300"))
			response = attribute300HttpResponse;
		else if(httpUriRequest.getURI().getSchemeSpecificPart().equals("/api/v2/sys_md_Attribute?start=400"))
			response = attribute400HttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/sys_md_EntityType"))
			response = entityHttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/org_molgenis_test_TypeTest"))
			response = dataHttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/base_Location"))
			response = locationHttpResponse;
		else if(httpUriRequest.getURI().getPath().equals("/api/v2/base_TypeTestRef"))
			response = refHttpResponse;

		return response;
	}

	@Override
	public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext)
			throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}

	@Override
	public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}

	@Override
	public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext)
			throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}

	@Override
	public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}

	@Override
	public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler,
			HttpContext httpContext) throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}

	@Override
	public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}

	@Override
	public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler,
			HttpContext httpContext) throws IOException, ClientProtocolException
	{
		throw new NotImplementedException();
	}
}
