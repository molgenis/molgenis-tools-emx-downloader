package org.molgenis.downloader.integration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

class TestHttpClient implements HttpClient {
  @Override
  public HttpParams getParams() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    throw new UnsupportedOperationException();
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
    HttpResponse versionHttpResponse = mock(HttpResponse.class);
    HttpEntity versionHttpEntity = mock(HttpEntity.class);
    when(versionHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/version.json"));

    when(versionHttpResponse.getEntity()).thenReturn(versionHttpEntity);

    HttpResponse languageHttpResponse = mock(HttpResponse.class);
    HttpEntity languageHttpEntity = mock(HttpEntity.class);
    when(languageHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/language.json"));
    when(languageHttpResponse.getEntity()).thenReturn(languageHttpEntity);

    HttpResponse tagHttpResponse = mock(HttpResponse.class);
    HttpEntity tagHttpEntity = mock(HttpEntity.class);
    when(tagHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/tags.json"));
    when(tagHttpResponse.getEntity()).thenReturn(tagHttpEntity);

    HttpResponse packageHttpResponse = mock(HttpResponse.class);
    HttpEntity packageHttpEntity = mock(HttpEntity.class);
    when(packageHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/package.json"));
    when(packageHttpResponse.getEntity()).thenReturn(packageHttpEntity);

    HttpResponse attributeHttpResponse = mock(HttpResponse.class);
    HttpEntity attributeHttpEntity = mock(HttpEntity.class);
    when(attributeHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/attributes.json"));
    when(attributeHttpResponse.getEntity()).thenReturn(attributeHttpEntity);

    HttpResponse attribute100HttpResponse = mock(HttpResponse.class);
    HttpEntity attribute100HttpEntity = mock(HttpEntity.class);
    when(attribute100HttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/attributes100.json"));
    when(attribute100HttpResponse.getEntity()).thenReturn(attribute100HttpEntity);

    HttpResponse attribute200HttpResponse = mock(HttpResponse.class);
    HttpEntity attribute200HttpEntity = mock(HttpEntity.class);
    when(attribute200HttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/attributes200.json"));
    when(attribute200HttpResponse.getEntity()).thenReturn(attribute200HttpEntity);

    HttpResponse attribute300HttpResponse = mock(HttpResponse.class);
    HttpEntity attribute300HttpEntity = mock(HttpEntity.class);
    when(attribute300HttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/attributes300.json"));
    when(attribute300HttpResponse.getEntity()).thenReturn(attribute300HttpEntity);

    HttpResponse attribute400HttpResponse = mock(HttpResponse.class);
    HttpEntity attribute400HttpEntity = mock(HttpEntity.class);
    when(attribute400HttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/attributes400.json"));
    when(attribute400HttpResponse.getEntity()).thenReturn(attribute400HttpEntity);

    HttpResponse entityHttpResponse = mock(HttpResponse.class);
    HttpEntity entityHttpEntity = mock(HttpEntity.class);
    when(entityHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/type.json"));
    when(entityHttpResponse.getEntity()).thenReturn(entityHttpEntity);

    HttpResponse dataHttpResponse = mock(HttpResponse.class);
    HttpEntity dataHttpEntity = mock(HttpEntity.class);
    when(dataHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/data.json"));
    when(dataHttpResponse.getEntity()).thenReturn(dataHttpEntity);

    HttpResponse locationHttpResponse = mock(HttpResponse.class);
    HttpEntity locationHttpEntity = mock(HttpEntity.class);
    when(locationHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/location.json"));
    when(locationHttpResponse.getEntity()).thenReturn(locationHttpEntity);

    HttpResponse refHttpResponse = mock(HttpResponse.class);
    HttpEntity refHttpEntity = mock(HttpEntity.class);
    when(refHttpEntity.getContent())
        .thenReturn(getClass().getResourceAsStream("/integration/ref.json"));
    when(refHttpResponse.getEntity()).thenReturn(refHttpEntity);

    HttpResponse response = null;

    if (httpUriRequest.getURI().getPath().equals("/api/v2/version")) response = versionHttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/sys_Language"))
      response = languageHttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/sys_md_Tag"))
      response = tagHttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/sys_md_Package"))
      response = packageHttpResponse;
    else if (httpUriRequest
            .getURI()
            .getSchemeSpecificPart()
            .equals("/api/v2/sys_md_Attribute?num=1")
        || httpUriRequest
            .getURI()
            .getSchemeSpecificPart()
            .equals("/api/v2/sys_md_Attribute?sort=id:asc")) response = attributeHttpResponse;
    else if (httpUriRequest
        .getURI()
        .getSchemeSpecificPart()
        .equals("/api/v2/sys_md_Attribute?start=100")) response = attribute100HttpResponse;
    else if (httpUriRequest
        .getURI()
        .getSchemeSpecificPart()
        .equals("/api/v2/sys_md_Attribute?start=200")) response = attribute200HttpResponse;
    else if (httpUriRequest
        .getURI()
        .getSchemeSpecificPart()
        .equals("/api/v2/sys_md_Attribute?start=300")) response = attribute300HttpResponse;
    else if (httpUriRequest
        .getURI()
        .getSchemeSpecificPart()
        .equals("/api/v2/sys_md_Attribute?start=400")) response = attribute400HttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/sys_md_EntityType"))
      response = entityHttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/org_molgenis_test_TypeTest"))
      response = dataHttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/base_Location"))
      response = locationHttpResponse;
    else if (httpUriRequest.getURI().getPath().equals("/api/v2/base_TypeTestRef"))
      response = refHttpResponse;

    return response;
  }

  @Override
  public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T execute(
      HttpUriRequest httpUriRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T execute(
      HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler)
      throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T execute(
      HttpHost httpHost,
      HttpRequest httpRequest,
      ResponseHandler<? extends T> responseHandler,
      HttpContext httpContext)
      throws IOException {
    throw new UnsupportedOperationException();
  }
}
