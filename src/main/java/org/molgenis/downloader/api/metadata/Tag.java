package org.molgenis.downloader.api.metadata;

public class Tag extends Metadata {

  private String label;
  private String objectIRI;
  private String relationIRI;
  private String relationLabel;
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
    if (this == o) return true;
    if (!(o instanceof Tag)) return false;

    Tag tag = (Tag) o;

    if (label != null ? !label.equals(tag.label) : tag.label != null) return false;
    if (objectIRI != null ? !objectIRI.equals(tag.objectIRI) : tag.objectIRI != null) return false;
    if (relationIRI != null ? !relationIRI.equals(tag.relationIRI) : tag.relationIRI != null)
      return false;
    if (relationLabel != null
        ? !relationLabel.equals(tag.relationLabel)
        : tag.relationLabel != null) return false;
    return codeSystem != null ? codeSystem.equals(tag.codeSystem) : tag.codeSystem == null;
  }

  @Override
  public int hashCode() {
    int result = label != null ? label.hashCode() : 0;
    result = 31 * result + (objectIRI != null ? objectIRI.hashCode() : 0);
    result = 31 * result + (relationIRI != null ? relationIRI.hashCode() : 0);
    result = 31 * result + (relationLabel != null ? relationLabel.hashCode() : 0);
    result = 31 * result + (codeSystem != null ? codeSystem.hashCode() : 0);
    return result;
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
        + ", objectIRI="
        + objectIRI
        + ", relationIRI="
        + relationIRI
        + ", relationLabel='"
        + relationLabel
        + '\''
        + ", codeSystem='"
        + codeSystem
        + '\''
        + '}';
  }
}
