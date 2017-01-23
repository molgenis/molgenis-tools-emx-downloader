package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.naming.directory.AttributeInUseException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class MolgenisV2MetadataConverterTest
{
	private MolgenisV2MetadataConverter converter;
	private WriteableMetadataRepository metadataRepository;

	@BeforeClass
	public void setup()
	{
		metadataRepository = mock(WriteableMetadataRepository.class);
		converter = new MolgenisV2MetadataConverter(metadataRepository);
	}

	@Test
	public void toPackageTest()
	{
		when(metadataRepository.createPackage("name")).thenReturn(new Package("name"));
		when(metadataRepository.createPackage("parent")).thenReturn(new Package("parent"));
		when(metadataRepository.createTag("tag")).thenReturn(new Tag("tag"));


		Attribute nameAttr = new Attribute("fullName", "fullName");
		Attribute descriptionAttr = new Attribute("description", "description");
		Attribute parentAttr = new Attribute("parent", "parent");
		Attribute tagAttr = new Attribute("tags", "tags");

		String name = "name";
		String desc = "desc";
		String parent = "parent";
		String tag = "tag";

		Map<Attribute, String> map = new HashMap<>();
		map.put(nameAttr, name);
		map.put(descriptionAttr, desc);
		map.put(parentAttr, parent);
		map.put(tagAttr, tag);

		Package actual = converter.toPackage(map);
		Package expected = new Package("name");
		Package parentPackage = new Package("parent");
		Tag expectedTag = new Tag("tag");

		expected.setDescription("desc");
		expected.setName("name");
		expected.setParent(parentPackage);
		expected.addTag(expectedTag);

		assertEquals(actual, expected);
	}

	@Test
	public void toTagTest()
	{
		when(metadataRepository.createTag("id")).thenReturn(new Tag("id"));

		Map<Attribute, String> map = new HashMap<>();
		map.put(new Attribute("id", "id"), "id");
		map.put(new Attribute("label", "label"), "label");
		map.put(new Attribute("objectIRI", "objectIRI"), "objectIRI");
		map.put(new Attribute("relationIRI", "relationIRI"), "relationIRI");
		map.put(new Attribute("relationLabel", "relationLabel"), "relationLabel");
		map.put(new Attribute("codeSystem", "codeSystem"), "codeSystem");

		Tag actual = converter.toTag(map);
		Tag expected = new Tag("id");

		expected.setId("id");
		expected.setLabel("label");
		expected.setCodeSystem("codeSystem");
		expected.setRelationIRI(URI.create("relationIRI"));
		expected.setRelationLabel("relationLabel");
		expected.setObjectIRI(URI.create("objectIRI"));

		assertEquals(actual, expected);
	}

	@Test
	public void toAttributeTest()
	{
		when(metadataRepository.createAttribute("id")).thenReturn(new Attribute("id"));

		Map<Attribute, String> map = new HashMap<>();
		map.put(new Attribute("id", "id"), "id");
		map.put(new Attribute("name", "name"), "name");
		map.put(new Attribute("dataType", "dataType"), "STRING");
		map.put(new Attribute("parts", "parts"), "");
		map.put(new Attribute("refEntity", "refEntity"), null);
		map.put(new Attribute("nillable", "nillable"), null);
		map.put(new Attribute("auto", "auto"), "FALSE");
		map.put(new Attribute("visible", "visible"), "TRUE");
		map.put(new Attribute("label", "label"), "label");
		map.put(new Attribute("description", "description"), "description");
		//aggregateable is false in the actual
		map.put(new Attribute("isAggregatable", "isAggregatable"), "TRUE");
		map.put(new Attribute("enumOptions", "enumOptions"), null);
		map.put(new Attribute("rangeMin", "rangeMin"), "");
		map.put(new Attribute("rangeMax", "rangeMax"), "");
		map.put(new Attribute("readOnly", "readOnly"), "FALSE");
		map.put(new Attribute("unique", "unique"), "FALSE");
		map.put(new Attribute("visibleExpression", "visibleExpression"), null);
		map.put(new Attribute("validateExpression", "validateExpression"), null);
		map.put(new Attribute("defaultValue", "defaultValue"), null);

		Attribute actual = converter.toAttribute(map);
		Attribute expected = new Attribute("id");
		expected.setId("id");
		expected.setName("name");
		expected.setDataType(DataType.STRING);
		expected.setAuto(false);
		expected.setVisible(true);
		expected.setLabel("label");
		expected.setDescription("description");
		expected.setAggregateable(true);
		expected.setEnumOptions(null);
		expected.setReadOnly(false);
		expected.setUnique(false);

		assertEquals(actual, expected);
	}

	@Test
	public void toEntityTest()
	{

	}
}
