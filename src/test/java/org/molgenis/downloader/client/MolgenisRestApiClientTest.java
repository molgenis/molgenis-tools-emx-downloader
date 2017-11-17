package org.molgenis.downloader.client;

import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.json.JSONObject;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class MolgenisRestApiClientTest
{
	@Test
	public void versionTest() throws IOException, URISyntaxException
	{
		HttpClient httpClient = mock(HttpClient.class);
		HttpResponse httpResponse = mock(HttpResponse.class);
		HttpEntity httpEntity = mock(HttpEntity.class);

		when(httpEntity.getContent()).thenReturn(getClass().getResourceAsStream("/versionResponse.txt"));
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpClient.execute(any())).thenReturn(httpResponse);

		MolgenisRestApiClient client = new MolgenisRestApiClient(httpClient, new URI(""));
		MolgenisVersion version = client.getVersion();
		MolgenisVersion expected = new MolgenisVersion(9, 9, 9);
		assertEquals(version, expected);
	}

	@Test
	public void getEntityTest() throws IOException, URISyntaxException
	{
		HttpClient httpClient = mock(HttpClient.class);
		HttpResponse httpResponse = mock(HttpResponse.class);
		HttpEntity httpEntity = mock(HttpEntity.class);

		when(httpEntity.getContent()).thenReturn(getClass().getResourceAsStream("/entities.txt"));
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpClient.execute(any())).thenReturn(httpResponse);

		MolgenisRestApiClient client = new MolgenisRestApiClient(httpClient, new URI(""));
		Entity actual = client.getEntity("test");

		Attribute idAttr = new Attribute("id").setName("id");
		idAttr.setEntityFullname("biobank");
		Attribute nameAttr = new Attribute("name").setName("name");
		nameAttr.setEntityFullname("biobank");
		Entity expected = new Entity("biobank");
		expected.setIdAttribute(idAttr);
		expected.setAttributes(Sets.newHashSet(idAttr, nameAttr));
		assertEquals(actual, expected);
	}

	@Test
	public void getAttributesTest() throws IOException, URISyntaxException
	{
		Entity ref = new Entity("ref").setIdAttribute(Attribute.createAttribute("value", "value"));
		Attribute idAttr = Attribute.createAttribute("id", "id").setEntityFullname("biobank");
		Attribute xbool = Attribute.createAttribute("xbool", "xbool").setEntityFullname("biobank");
		Attribute xboolnillable = Attribute.createAttribute("xboolnillable", "xboolnillable")
										   .setEntityFullname("biobank");
		Attribute xcompound = Attribute.createAttribute("xcompound", "xcompound")
									   .setEntityFullname("biobank")
									   .setDataType(DataType.COMPOUND);
		Attribute xcompound_int = Attribute.createAttribute("xcompound_int", "xcompound_int")
										   .setEntityFullname("biobank")
										   .setDataType(DataType.COMPOUND);
		Attribute xcompound_string = Attribute.createAttribute("xcompound_string", "xcompound_string")
											  .setEntityFullname("biobank")
											  .setDataType(DataType.COMPOUND);
		Attribute xcategorical_value = Attribute.createAttribute("xcategorical_value", "xcategorical_value")
												.setEntityFullname("biobank");
		Attribute xcategoricalnillable_value = Attribute.createAttribute("xcategoricalnillable_value",
				"xcategoricalnillable_value").setEntityFullname("biobank");
		Attribute xcatmrefnillable_value = Attribute.createAttribute("xcatmrefnillable_value", "xcatmrefnillable_value")
													.setEntityFullname("biobank");
		Attribute xdate = Attribute.createAttribute("xdate", "xdate").setEntityFullname("biobank");
		Attribute xdatenillable = Attribute.createAttribute("xdatenillable", "xdatenillable")
										   .setEntityFullname("biobank");
		Attribute xdatetime = Attribute.createAttribute("xdatetime", "xdatetime").setEntityFullname("biobank");
		Attribute xdatetimenillable = Attribute.createAttribute("xdatetimenillable", "xdatetimenillable")
											   .setEntityFullname("biobank");
		Attribute xdecimal = Attribute.createAttribute("xdecimal", "xdecimal").setEntityFullname("biobank");
		Attribute xdecimalnillable = Attribute.createAttribute("xdecimalnillable", "xdecimalnillable")
											  .setEntityFullname("biobank");
		Attribute xemail = Attribute.createAttribute("xemail", "xemail").setEntityFullname("biobank");
		Attribute xemailnillable = Attribute.createAttribute("xemailnillable", "xemailnillable")
											.setEntityFullname("biobank");
		Attribute xenum = Attribute.createAttribute("xenum", "xenum").setEntityFullname("biobank");
		Attribute xenumnillable = Attribute.createAttribute("xenumnillable", "xenumnillable")
										   .setEntityFullname("biobank");
		Attribute xhtml = Attribute.createAttribute("xhtml", "xhtml").setEntityFullname("biobank");
		Attribute xhtmlnillable = Attribute.createAttribute("xhtmlnillable", "xhtmlnillable")
										   .setEntityFullname("biobank");
		Attribute xhyperlink = Attribute.createAttribute("xhyperlink", "xhyperlink").setEntityFullname("biobank");
		Attribute xhyperlinknillable = Attribute.createAttribute("xhyperlinknillable", "xhyperlinknillable")
												.setEntityFullname("biobank");
		Attribute xint = Attribute.createAttribute("xint", "xint").setEntityFullname("biobank");
		Attribute xintnillable = Attribute.createAttribute("xintnillable", "xintnillable").setEntityFullname("biobank");
		Attribute xintrange = Attribute.createAttribute("xintrange", "xintrange").setEntityFullname("biobank");
		Attribute xintrangenillable = Attribute.createAttribute("xintrangenillable", "xintrangenillable")
											   .setEntityFullname("biobank");
		Attribute xlong = Attribute.createAttribute("xlong", "xlong").setEntityFullname("biobank");
		Attribute xlongnillable = Attribute.createAttribute("xlongnillable", "xlongnillable")
										   .setEntityFullname("biobank");
		Attribute xlongrange = Attribute.createAttribute("xlongrange", "xlongrange").setEntityFullname("biobank");
		Attribute xlongrangenillable = Attribute.createAttribute("xlongrangenillable", "xlongrangenillable")
												.setEntityFullname("biobank");
		Attribute xmref_value = Attribute.createAttribute("xmref_value", "xmref_value")
										 .setEntityFullname("biobank")
										 .setDataType(DataType.MREF)
										 .setRefEntity(ref);
		Attribute xmrefnillable_value = Attribute.createAttribute("xmrefnillable_value", "xmrefnillable_value")
												 .setEntityFullname("biobank")
												 .setDataType(DataType.MREF)
												 .setRefEntity(ref);
		Attribute xstring = Attribute.createAttribute("xstring", "xstring").setEntityFullname("biobank");
		Attribute xstringnillable = Attribute.createAttribute("xstringnillable", "xstringnillable")
											 .setEntityFullname("biobank");
		Attribute xtext = Attribute.createAttribute("xtext", "xtext").setEntityFullname("biobank");
		Attribute xtextnillable = Attribute.createAttribute("xtextnillable", "xtextnillable")
										   .setEntityFullname("biobank");
		Attribute xxref_value = Attribute.createAttribute("xxref_value", "xxref_value")
										 .setEntityFullname("biobank")
										 .setDataType(DataType.XREF)
										 .setRefEntity(ref);
		Attribute xxrefnillable_value = Attribute.createAttribute("xxrefnillable_value", "xxrefnillable_value")
												 .setEntityFullname("biobank")
												 .setDataType(DataType.XREF)
												 .setRefEntity(ref);
		Attribute xstring_hidden = Attribute.createAttribute("xstring_hidden", "xstring_hidden")
											.setEntityFullname("biobank");
		Attribute xstringnillable_hidden = Attribute.createAttribute("xstringnillable_hidden", "xstringnillable_hidden")
													.setEntityFullname("biobank");
		Attribute xstring_unique = Attribute.createAttribute("xstring_unique", "xstring_unique")
											.setEntityFullname("biobank");
		Attribute xint_unique = Attribute.createAttribute("xint_unique", "xint_unique").setEntityFullname("biobank");
		Attribute xxref_unique = Attribute.createAttribute("xxref_unique", "xxref_unique").setEntityFullname("biobank");
		Attribute xfile = Attribute.createAttribute("xfile", "xfile").setEntityFullname("biobank");
		Attribute xcomputedxref = Attribute.createAttribute("xcomputedxref", "xcomputedxref")
										   .setEntityFullname("biobank");
		Attribute xcomputedint = Attribute.createAttribute("xcomputedint", "xcomputedint").setEntityFullname("biobank");

		HttpClient httpClient = mock(HttpClient.class);
		MolgenisRestApiClient client = new MolgenisRestApiClient(httpClient, new URI(""));

		String jsonString = IOUtils.toString(getClass().getResourceAsStream("/attributes.txt"), "UTF-8");
		JSONObject json = new JSONObject(jsonString);

		Set<Attribute> attributes = Sets.newHashSet(idAttr, xbool, xboolnillable, xcompound, xcompound_int,
				xcompound_string, xcategorical_value, xcategoricalnillable_value, xcatmrefnillable_value, xdate,
				xdatenillable, xdatetime, xdatetimenillable, xdecimal, xdecimalnillable, xemail, xemailnillable, xenum,
				xenumnillable, xhtml, xhtmlnillable, xhyperlink, xhyperlinknillable, xint, xintnillable, xintrange,
				xintrangenillable, xlong, xlongnillable, xlongrange, xlongrangenillable, xmref_value,
				xmrefnillable_value, xstring, xstringnillable, xtext, xtextnillable, xxref_value, xxrefnillable_value,
				xstring_hidden, xstringnillable_hidden, xstring_unique, xint_unique, xxref_unique, xfile, xcomputedxref,
				xcomputedint);

		Map<String, String> actual = client.getAttributes(json, attributes);

		assertEquals(
				"{xint_unique=1, xcategoricalnillable_value={\"_href\":\"/api/v2/base_TypeTestRef/ref1\",\"label\":\"label1\",\"value\":\"ref1\"}, xstringnillable_hidden=hidden, xxref_unique={\"_href\":\"/api/v2/base_TypeTestRef/ref1\",\"label\":\"label1\",\"value\":\"ref1\"}, xstring_hidden=hidden, xstring=str1, xhtmlnillable=<h1>html</h1>, xdatetime=1985-08-12T08:12:13+0200, xint=5, xboolnillable=true, xdatetimenillable=1985-08-12T08:12:13+0200, xtextnillable=text, xcomputedint=5, xcatmrefnillable_value=[{\"_href\":\"/api/v2/base_TypeTestRef/ref1\",\"label\":\"label1\",\"value\":\"ref1\"}], xstring_unique=str1, xenumnillable=enum1, xhyperlink=http://www.molgenis.org/, xlongrange=2, id=1, xxrefnillable_value=ref1, xdatenillable=1985-08-01, xdecimal=1.23, xemail=molgenis@gmail.com, xdecimalnillable=1.23, xintrange=1, xbool=true, xstringnillable=str1, xcomputedxref={\"_href\":\"/api/v2/base_Location/5\",\"Position\":5}, xdate=1985-08-01, xhtml=<h1>html</h1>, xxref_value=ref1, xlongnillable=1, xemailnillable=molgenis@gmail.com, xintnillable=1, xenum=enum1, xhyperlinknillable=http://www.molgenis.org/, xintrangenillable=2, xtext=text, xcategorical_value={\"_href\":\"/api/v2/base_TypeTestRef/ref1\",\"label\":\"label1\",\"value\":\"ref1\"}, xmrefnillable_value=ref1, xlong=1, xlongrangenillable=2, xmref_value=ref1, xfile=}",
				actual.toString());

	}
}
