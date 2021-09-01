package org.molgenis.downloader.api.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Entity extends Metadata {

  private String fullName;
  private Set<Attribute> attributes;
  private Entity base;
  private Package pkg;
  private boolean abstractClass;
  private String description;
  private Map<Language, String> descriptions;
  private String label;
  private Map<Language, String> labels;
  private Set<Tag> tags;
  private Backend backend;
  private Attribute idAttribute;
  private Attribute labelAttribute;
  private Set<Attribute> lookupAttributes;
  private boolean rowLevelSecured;

  public Entity() {
    attributes = new LinkedHashSet<>();
    descriptions = new HashMap<>();
    labels = new HashMap<>();
    tags = new HashSet<>();
    lookupAttributes = new HashSet<>();
  }

  public Entity(final String fullName) {
    this();
    this.fullName = fullName;
  }

  public Entity(final String shortName, final String pkg) {
    this();
    fullName = pkg + "_" + shortName;
  }

  public static Entity createEntityByName(final String fullName) {
    if (fullName == null || fullName.isEmpty()) {
      throw new IllegalArgumentException();
    }
    return new Entity(fullName);
  }

  public boolean isRowLevelSecured() {
    return rowLevelSecured;
  }

  public Entity setRowLevelSecured(boolean rowLevelSecured) {
    this.rowLevelSecured = rowLevelSecured;
    return this;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 59 * hash + Objects.hashCode(this.fullName);
    hash = 59 * hash + Objects.hashCode(this.pkg);
    return hash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Entity entity = (Entity) o;

    if (abstractClass != entity.abstractClass) return false;
    if (rowLevelSecured != entity.rowLevelSecured) return false;
    if (fullName != null ? !fullName.equals(entity.fullName) : entity.fullName != null)
      return false;
    if (attributes != null ? !attributes.equals(entity.attributes) : entity.attributes != null)
      return false;
    if (base != null ? !base.equals(entity.base) : entity.base != null) return false;
    if (pkg != null ? !pkg.equals(entity.pkg) : entity.pkg != null) return false;
    if (description != null ? !description.equals(entity.description) : entity.description != null)
      return false;
    if (descriptions != null
        ? !descriptions.equals(entity.descriptions)
        : entity.descriptions != null) return false;
    if (label != null ? !label.equals(entity.label) : entity.label != null) return false;
    if (labels != null ? !labels.equals(entity.labels) : entity.labels != null) return false;
    if (tags != null ? !tags.equals(entity.tags) : entity.tags != null) return false;
    if (backend != entity.backend) return false;
    if (idAttribute != null ? !idAttribute.equals(entity.idAttribute) : entity.idAttribute != null)
      return false;
    return (labelAttribute != null
            ? labelAttribute.equals(entity.labelAttribute)
            : entity.labelAttribute == null)
        && (lookupAttributes != null
            ? lookupAttributes.equals(entity.lookupAttributes)
            : entity.lookupAttributes == null);
  }

  public boolean isParentOf(final Entity entity) {
    if (entity == null || entity.getBase() == null) {
      return false;
    }
    return this.equals(entity.getBase()) || this.isParentOf(entity.getBase());
  }

  public Attribute getLabelAttribute() {
    return labelAttribute;
  }

  public Entity setLabelAttribute(Attribute labelAttribute) {
    this.labelAttribute = labelAttribute;
    return this;
  }

  public Set<Attribute> getLookupAttributes() {
    return lookupAttributes;
  }

  public Entity setLookupAttributes(Set<Attribute> lookupAttributes) {
    this.lookupAttributes = lookupAttributes;
    return this;
  }

  public Attribute getIdAttribute() {
    return idAttribute;
  }

  public Entity setIdAttribute(Attribute idAttribute) {
    this.idAttribute = idAttribute;
    return this;
  }

  public Entity getBase() {
    return base;
  }

  public Entity setBase(Entity base) {
    this.base = base;
    return this;
  }

  public Package getPackage() {
    return pkg;
  }

  public Entity setPackage(Package pkg) {
    this.pkg = pkg;
    return this;
  }

  public boolean isAbstractClass() {
    return abstractClass;
  }

  public Entity setAbstractClass(boolean abstractClass) {
    this.abstractClass = abstractClass;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Entity setDescription(String description) {
    this.description = description;
    return this;
  }

  public Map<Language, String> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(Map<Language, String> descriptions) {
    this.descriptions = descriptions;
  }

  public String getLabel() {
    return label;
  }

  public Entity setLabel(String label) {
    this.label = label;
    return this;
  }

  public Map<Language, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<Language, String> labels) {
    this.labels = labels;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public Backend getBackend() {
    return backend;
  }

  public Entity setBackend(Backend backend) {
    this.backend = backend;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  public Entity setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public Set<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(Set<Attribute> attributes) {
    this.attributes = attributes;
  }

  public Entity addAttribute(final Attribute att) {
    attributes.add(att);
    return this;
  }

  public Entity addLookupAttribute(final Attribute att) {
    attributes.add(att);
    return this;
  }

  public void addTag(final Tag tag) {
    tags.add(tag);
  }

  public void addDescription(final String description, final Language language) {
    descriptions.put(language, description);
  }

  public void addLabel(final String label, final Language language) {
    labels.put(language, label);
  }

  @Override
  public String toString() {
    return "Entity{"
        + "fullName='"
        + fullName
        + '\''
        + ", attributes="
        + attributes
        + ", base="
        + base
        + ", pkg="
        + pkg
        + ", abstractClass="
        + abstractClass
        + ", description='"
        + description
        + '\''
        + ", descriptions="
        + descriptions
        + ", label='"
        + label
        + '\''
        + ", labels="
        + labels
        + ", tags="
        + tags
        + ", backend="
        + backend
        + ", idAttribute="
        + idAttribute
        + ", labelAttribute="
        + labelAttribute
        + ", lookupAttributes="
        + lookupAttributes
        + ", rowLevelSecured="
        + rowLevelSecured
        + '}';
  }

  public List<String> getAttributeNames() {
    List<Attribute> list = new ArrayList<>(getAttributes());
    return list.stream().map(Attribute::getName).collect(Collectors.toList());
  }
}
