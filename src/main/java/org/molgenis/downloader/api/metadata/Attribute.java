package org.molgenis.downloader.api.metadata;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Attribute extends Metadata implements Comparable<Attribute> {
  private final Set<Tag> tags;
  private final Map<Language, String> labels;
  private final Map<Language, String> descriptions;
  private final List<Attribute> parts;
  private String entityFullname;
  private String name;
  private DataType dataType;
  private Entity refEntity;
  private boolean idAttribute;
  private boolean lookupAttribute;
  private boolean nillable;
  private String nullableExpression;
  private boolean auto;
  private boolean visible;
  private boolean readOnly;
  private boolean unique;
  private boolean aggregateable;
  private boolean labelAttribute;
  private String enumOptions;
  private String expression;
  private String label;
  private String description;
  private String visibleExpression;
  private String validationExpression;
  private String defaultValue;
  private String orderBy;
  private Attribute mappedBy;
  private Integer maxLength;
  private Integer rangeMin;
  private Integer rangeMax;
  private Attribute compound;
  private String entityId;

  public Attribute(final String id) {
    super.setId(id);
    labels = new HashMap<>();
    descriptions = new HashMap<>();
    tags = new HashSet<>();
    parts = new ArrayList<>();
    visible = true;
    dataType = DataType.STRING;
  }

  public static Attribute createAttribute(final String id, final String name) {
    return new Attribute(id).setName(name);
  }

  public static Attribute from(final String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException();
    }
    final Attribute att = new Attribute(id);
    att.setName(id);
    return att;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Attribute attribute = (Attribute) o;
    return idAttribute == attribute.idAttribute
        && lookupAttribute == attribute.lookupAttribute
        && nillable == attribute.nillable
        && auto == attribute.auto
        && visible == attribute.visible
        && readOnly == attribute.readOnly
        && unique == attribute.unique
        && aggregateable == attribute.aggregateable
        && labelAttribute == attribute.labelAttribute
        && Objects.equals(entityFullname, attribute.entityFullname)
        && Objects.equals(name, attribute.name)
        && dataType == attribute.dataType
        && Objects.equals(refEntity, attribute.refEntity)
        && Objects.equals(nullableExpression, attribute.nullableExpression)
        && Objects.equals(enumOptions, attribute.enumOptions)
        && Objects.equals(expression, attribute.expression)
        && Objects.equals(label, attribute.label)
        && Objects.equals(description, attribute.description)
        && Objects.equals(visibleExpression, attribute.visibleExpression)
        && Objects.equals(validationExpression, attribute.validationExpression)
        && Objects.equals(defaultValue, attribute.defaultValue)
        && Objects.equals(orderBy, attribute.orderBy)
        && Objects.equals(mappedBy, attribute.mappedBy)
        && Objects.equals(maxLength, attribute.maxLength)
        && Objects.equals(rangeMin, attribute.rangeMin)
        && Objects.equals(rangeMax, attribute.rangeMax)
        && Objects.equals(tags, attribute.tags)
        && Objects.equals(labels, attribute.labels)
        && Objects.equals(descriptions, attribute.descriptions)
        && Objects.equals(parts, attribute.parts)
        && Objects.equals(compound, attribute.compound)
        && Objects.equals(entityId, attribute.entityId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        entityFullname,
        name,
        dataType,
        refEntity,
        idAttribute,
        lookupAttribute,
        nillable,
        nullableExpression,
        auto,
        visible,
        readOnly,
        unique,
        aggregateable,
        labelAttribute,
        enumOptions,
        expression,
        label,
        description,
        visibleExpression,
        validationExpression,
        defaultValue,
        orderBy,
        mappedBy,
        maxLength,
        rangeMin,
        rangeMax,
        tags,
        labels,
        descriptions,
        compound,
        entityId);
  }

  public Attribute getMappedBy() {
    return mappedBy;
  }

  public void setMappedBy(Attribute mappedBy) {
    this.mappedBy = mappedBy;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public boolean isLabelAttribute() {
    return labelAttribute;
  }

  public Attribute setLabelAttribute(boolean labelAttribute) {
    this.labelAttribute = labelAttribute;
    return this;
  }

  public List<Attribute> getParts() {
    return parts;
  }

  public Attribute getCompound() {
    return compound;
  }

  public Attribute setCompound(Attribute compound) {
    this.compound = compound;
    return this;
  }

  public String getEntityFullname() {
    return entityFullname;
  }

  public Attribute setEntityFullname(String entityFullname) {
    this.entityFullname = entityFullname;
    return this;
  }

  public Entity getRefEntity() {
    return refEntity;
  }

  public Attribute setRefEntity(Entity refEntity) {
    this.refEntity = refEntity;
    return this;
  }

  public boolean isIdAttribute() {
    return idAttribute;
  }

  public Attribute setIdAttribute(boolean idAttribute) {
    this.idAttribute = idAttribute;
    return this;
  }

  public boolean isLookupAttribute() {
    return lookupAttribute;
  }

  public Attribute setLookupAttribute(boolean lookupAttribute) {
    this.lookupAttribute = lookupAttribute;
    return this;
  }

  public String getExpression() {
    return expression;
  }

  public Attribute setExpression(String expression) {
    this.expression = expression;
    return this;
  }

  public boolean isAuto() {
    return auto;
  }

  public Attribute setAuto(boolean auto) {
    this.auto = auto;
    return this;
  }

  public boolean isVisible() {
    return visible;
  }

  public Attribute setVisible(boolean visible) {
    this.visible = visible;
    return this;
  }

  public String getLabel() {
    return label;
  }

  public Attribute setLabel(String label) {
    this.label = label;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Attribute setDescription(String description) {
    this.description = description;
    return this;
  }

  public boolean isAggregateable() {
    return aggregateable;
  }

  public Attribute setAggregateable(boolean aggregateable) {
    this.aggregateable = aggregateable;
    return this;
  }

  public Integer getMaxLength() {
    return maxLength;
  }

  public Attribute setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  public Integer getRangeMin() {
    return rangeMin;
  }

  public Attribute setRangeMin(Integer rangeMin) {
    this.rangeMin = rangeMin;
    return this;
  }

  public Integer getRangeMax() {
    return rangeMax;
  }

  public Attribute setRangeMax(Integer rangeMax) {
    this.rangeMax = rangeMax;
    return this;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public Attribute setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    return this;
  }

  public boolean isUnique() {
    return unique;
  }

  public Attribute setUnique(boolean unique) {
    this.unique = unique;
    return this;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public String getVisibleExpression() {
    return visibleExpression;
  }

  public Attribute setVisibleExpression(String visibleExpression) {
    this.visibleExpression = visibleExpression;
    return this;
  }

  public String getValidationExpression() {
    return validationExpression;
  }

  public Attribute setValidationExpression(String validationExpression) {
    this.validationExpression = validationExpression;
    return this;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public Attribute setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  public Map<Language, String> getLabels() {
    return labels;
  }

  public Map<Language, String> getDescriptions() {
    return descriptions;
  }

  public String getName() {
    return name;
  }

  public Attribute setName(String name) {
    this.name = name;
    return this;
  }

  public DataType getDataType() {
    return dataType;
  }

  public Attribute setDataType(DataType dataType) {
    this.dataType = dataType;
    return this;
  }

  public boolean isNillable() {
    return nillable;
  }

  public Attribute setNillable(boolean nillable) {
    this.nillable = nillable;
    return this;
  }

  public String getNullableExpression() {
    return nullableExpression;
  }

  public Attribute setNullableExpression(String nullableExpression) {
    this.nullableExpression = nullableExpression;
    return this;
  }

  public String getEnumOptions() {
    return enumOptions;
  }

  public Attribute setEnumOptions(String enumOptions) {
    this.enumOptions = enumOptions;
    return this;
  }

  public String getEntityId() {
    return this.entityId;
  }

  public Attribute setEntityId(String id) {
    this.entityId = id;
    return this;
  }

  public Attribute addPart(final Attribute part) {
    parts.add(part);
    return this;
  }

  public Attribute addTag(final Tag tag) {
    tags.add(tag);
    return this;
  }

  public Attribute addDescription(final String description, final Language language) {
    descriptions.put(language, description);
    return this;
  }

  public void addLabel(final String label, final Language language) {
    labels.put(language, label);
  }

  @Override
  public String toString() {
    return "Attribute{"
        + "entityFullname='"
        + entityFullname
        + '\''
        + ", name='"
        + name
        + '\''
        + ", dataType="
        + dataType
        + ", refEntity="
        + refEntity
        + ", idAttribute="
        + idAttribute
        + ", lookupAttribute="
        + lookupAttribute
        + ", nillable="
        + nillable
        + ", nillableExpression="
        + nullableExpression
        + ", auto="
        + auto
        + ", visible="
        + visible
        + ", readOnly="
        + readOnly
        + ", unique="
        + unique
        + ", aggregateable="
        + aggregateable
        + ", labelAttribute="
        + labelAttribute
        + ", enumOptions='"
        + enumOptions
        + '\''
        + ", expression='"
        + expression
        + '\''
        + ", label='"
        + label
        + '\''
        + ", description='"
        + description
        + '\''
        + ", visibleExpression='"
        + visibleExpression
        + '\''
        + ", validationExpression='"
        + validationExpression
        + '\''
        + ", defaultValue='"
        + defaultValue
        + '\''
        + ", orderBy='"
        + orderBy
        + '\''
        + ", mappedBy="
        + mappedBy
        + ", rangeMin="
        + rangeMin
        + ", rangeMax="
        + rangeMax
        + ", tags="
        + tags
        + ", labels="
        + labels
        + ", descriptions="
        + descriptions
        + ", parts="
        + parts.stream().map(Attribute::getName).collect(toList())
        + ", compound="
        + compound
        + ", entityId='"
        + entityId
        + '\''
        + '}';
  }

  @Override
  public int compareTo(Attribute attr) {
    return id.compareTo(attr.getId());
  }
}
