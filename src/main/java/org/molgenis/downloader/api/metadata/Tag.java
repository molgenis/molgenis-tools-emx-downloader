package org.molgenis.downloader.api.metadata;

import java.util.Objects;

public class Tag extends Metadata {

  private String label;
  private String objectIRI;
  private String relationIRI;
  private String relationLabel;
  private String value;
  private String codeSystem;

  public Tag(final String id) {
    super.id = id;
  }

  public static Tag from(final String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException();
    }
    return new Tag(id);
  }

  public String getObjectIRI() {
    return objectIRI;
  }

  public void setObjectIRI(String objectIRI) {
    this.objectIRI = objectIRI;
  }

  public String getRelationIRI() {
    return relationIRI;
  }

  public void setRelationIRI(String relationIRI) {
    this.relationIRI = relationIRI;
  }

  public String getRelationLabel() {
    return relationLabel;
  }

  public void setRelationLabel(String relationLabel) {
    this.relationLabel = relationLabel;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getCodeSystem() {
    return codeSystem;
  }

  public void setCodeSystem(String codeSystem) {
    this.codeSystem = codeSystem;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tag tag = (Tag) o;
    return Objects.equals(getLabel(), tag.getLabel())
        && Objects.equals(getObjectIRI(), tag.getObjectIRI())
        && Objects.equals(getRelationIRI(), tag.getRelationIRI())
        && Objects.equals(getRelationLabel(), tag.getRelationLabel())
        && Objects.equals(getValue(), tag.getValue())
        && Objects.equals(getCodeSystem(), tag.getCodeSystem());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getLabel(),
        getObjectIRI(),
        getRelationIRI(),
        getRelationLabel(),
        getValue(),
        getCodeSystem());
  }

  @Override
  public String toString() {
    return "Tag{"
        + "id='"
        + id
        + '\''
        + ", label='"
        + label
        + '\''
        + ", objectIRI='"
        + objectIRI
        + '\''
        + ", relationIRI='"
        + relationIRI
        + '\''
        + ", relationLabel='"
        + relationLabel
        + '\''
        + ", value='"
        + value
        + '\''
        + ", codeSystem='"
        + codeSystem
        + '\''
        + '}';
  }
}
