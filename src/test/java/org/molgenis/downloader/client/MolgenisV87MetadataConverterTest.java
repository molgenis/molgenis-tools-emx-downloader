package org.molgenis.downloader.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MolgenisV87MetadataConverterTest {
  private MolgenisV87MetadataConverter converter;
  private WriteableMetadataRepository metadataRepository;

  @BeforeClass
  public void setup() {
    metadataRepository = mock(WriteableMetadataRepository.class);
    converter = new MolgenisV87MetadataConverter(metadataRepository);
  }

  @Test
  public void toPackageTest() {
    when(metadataRepository.createPackage("id")).thenReturn(new Package("id"));
    when(metadataRepository.createPackage("parent")).thenReturn(new Package("parent"));
    when(metadataRepository.createTag("tag")).thenReturn(new Tag("tag"));

    String id = "id";
    String label = "label";
    String desc = "desc";
    String parent = "parent";
    String tag = "tag";

    Map<String, String> map = new HashMap<>();
    map.put("id", id);
    map.put("label", label);
    map.put("description", desc);
    map.put("parent", parent);
    map.put("tags", tag);

    Package actual = converter.toPackage(map);
    Package expected = new Package(id);
    Package parentPackage = new Package("parent");
    Tag expectedTag = new Tag("tag");

    expected.setDescription("desc");
    expected.setName("id");
    expected.setLabel("label");
    expected.setParent(parentPackage);
    expected.addTag(expectedTag);

    assertEquals(actual, expected);
  }

  @Test
  public void toTagTest() {
    when(metadataRepository.createTag("id")).thenReturn(new Tag("id"));

    Map<String, String> map = new HashMap<>();
    map.put("id", "id");
    map.put("label", "label");
    map.put("objectIRI", "objectIRI");
    map.put("relationIRI", "relationIRI");
    map.put("relationLabel", "relationLabel");
    map.put("codeSystem", "codeSystem");

    Tag actual = converter.toTag(map);
    Tag expected = new Tag("id");

    expected.setId("id");
    expected.setLabel("label");
    expected.setCodeSystem("codeSystem");
    expected.setRelationIRI("relationIRI");
    expected.setRelationLabel("relationLabel");
    expected.setObjectIRI("objectIRI");

    assertEquals(actual, expected);
  }

  @Test
  public void toAttributeTest() {
    when(metadataRepository.createAttribute("id")).thenReturn(new Attribute("id"));

    Map<String, String> map = new HashMap<>();
    map.put("id", "id");
    map.put("name", "name");
    map.put("dataType", "STRING");
    map.put("parts", "");
    map.put("refEntity", null);
    map.put("nillable", null);
    map.put("auto", "FALSE");
    map.put("visible", "TRUE");
    map.put("label", "label");
    map.put("description", "description");
    map.put("isAggregatable", "TRUE");
    map.put("enumOptions", null);
    map.put("maxLength", "218");
    map.put("rangeMin", "");
    map.put("rangeMax", "");
    map.put("readOnly", "FALSE");
    map.put("unique", "FALSE");
    map.put("visibleExpression", null);
    map.put("validateExpression", null);
    map.put("defaultValue", null);

    Attribute actual = converter.toAttribute(map);
    Attribute expected = new Attribute("id");
    expected.setId("id");
    expected.setName("name");
    expected.setDataType(DataType.STRING);
    expected.setMaxLength(218);
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
  public void toEntityTest() {
    when(metadataRepository.createEntity("id")).thenReturn(new Entity("id"));
    when(metadataRepository.createPackage("package")).thenReturn(new Package("package"));
    when(metadataRepository.createAttribute("idAttribute"))
        .thenReturn(new Attribute("idAttribute").setName("idAttribute"));
    when(metadataRepository.createAttribute("labelAttribute"))
        .thenReturn(new Attribute("labelAttribute").setName("labelAttribute"));

    Map<String, String> map = new HashMap<>();
    map.put("id", "id");
    map.put("backend", "PostgreSQL");
    map.put("package", "package");
    map.put("lookupAttributes", "");
    map.put("abstract", "false");
    map.put("label", "label");
    map.put("description", "description");

    Entity actual = converter.toEntity(map);
    Entity expected = new Entity("id");
    expected.setFullName("id");
    expected.setBackend(Backend.POSTGRESQL);
    expected.setPackage(new Package("package"));
    expected.setAbstractClass(false);
    expected.setLabel("label");
    expected.setDescription("description");

    assertEquals(actual, expected);
  }
}
