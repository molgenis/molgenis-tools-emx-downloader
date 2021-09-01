package org.molgenis.downloader.api.metadata;

import java.util.*;

import static java.util.stream.Collectors.toList;

public final class Attribute extends Metadata implements Comparable<Attribute> {
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
    private final Set<Tag> tags;
    private final Map<Language, String> labels;
    private final Map<Language, String> descriptions;
    private final List<Attribute> parts;
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
        return idAttribute == attribute.idAttribute && lookupAttribute == attribute.lookupAttribute && nillable == attribute.nillable && auto == attribute.auto && visible == attribute.visible && readOnly == attribute.readOnly && unique == attribute.unique && aggregateable == attribute.aggregateable && labelAttribute == attribute.labelAttribute && Objects.equals(entityFullname, attribute.entityFullname) && Objects.equals(name, attribute.name) && dataType == attribute.dataType && Objects.equals(refEntity, attribute.refEntity) && Objects.equals(nullableExpression, attribute.nullableExpression) && Objects.equals(enumOptions, attribute.enumOptions) && Objects.equals(expression, attribute.expression) && Objects.equals(label, attribute.label) && Objects.equals(description, attribute.description) && Objects.equals(visibleExpression, attribute.visibleExpression) && Objects.equals(validationExpression, attribute.validationExpression) && Objects.equals(defaultValue, attribute.defaultValue) && Objects.equals(orderBy, attribute.orderBy) && Objects.equals(mappedBy, attribute.mappedBy) && Objects.equals(maxLength, attribute.maxLength) && Objects.equals(rangeMin, attribute.rangeMin) && Objects.equals(rangeMax, attribute.rangeMax) && Objects.equals(tags, attribute.tags) && Objects.equals(labels, attribute.labels) && Objects.equals(descriptions, attribute.descriptions) && Objects.equals(parts, attribute.parts) && Objects.equals(compound, attribute.compound) && Objects.equals(entityId, attribute.entityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityFullname, name, dataType, refEntity, idAttribute, lookupAttribute, nillable, nullableExpression, auto, visible, readOnly, unique, aggregateable, labelAttribute, enumOptions, expression, label, description, visibleExpression, validationExpression, defaultValue, orderBy, mappedBy, maxLength, rangeMin, rangeMax, tags, labels, descriptions, compound, entityId);
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

    public List<Attribute> getParts() {
        return parts;
    }

    public Attribute getCompound() {
        return compound;
    }

    public String getEntityFullname() {
        return entityFullname;
    }

    public Entity getRefEntity() {
        return refEntity;
    }

    public boolean isIdAttribute() {
        return idAttribute;
    }

    public boolean isLookupAttribute() {
        return lookupAttribute;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isAuto() {
        return auto;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAggregateable() {
        return aggregateable;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Integer getRangeMin() {
        return rangeMin;
    }

    public Integer getRangeMax() {
        return rangeMax;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isUnique() {
        return unique;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getVisibleExpression() {
        return visibleExpression;
    }

    public String getValidationExpression() {
        return validationExpression;
    }

    public String getDefaultValue() {
        return defaultValue;
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

    public DataType getDataType() {
        return dataType;
    }

    public boolean isNillable() {
        return nillable;
    }

    public String getNullableExpression() {
        return nullableExpression;
    }

    public String getEnumOptions() {
        return enumOptions;
    }

    public Attribute setEntityFullname(String entityFullname) {
        this.entityFullname = entityFullname;
        return this;
    }

    public Attribute setEntityId(String id) {
        this.entityId = id;
        return this;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public Attribute setName(String name) {
        this.name = name;
        return this;
    }

    public Attribute setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public Attribute setRefEntity(Entity refEntity) {
        this.refEntity = refEntity;
        return this;
    }

    public Attribute setIdAttribute(boolean idAttribute) {
        this.idAttribute = idAttribute;
        return this;
    }

    public Attribute setLookupAttribute(boolean lookupAttribute) {
        this.lookupAttribute = lookupAttribute;
        return this;
    }

    public Attribute setNillable(boolean nillable) {
        this.nillable = nillable;
        return this;
    }

    public Attribute setNullableExpression(String nullableExpression) {
        this.nullableExpression = nullableExpression;
        return this;
    }

    public Attribute setAuto(boolean auto) {
        this.auto = auto;
        return this;
    }

    public Attribute setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Attribute setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public Attribute setUnique(boolean unique) {
        this.unique = unique;
        return this;
    }

    public Attribute setAggregateable(boolean aggregateable) {
        this.aggregateable = aggregateable;
        return this;
    }

    public Attribute setLabelAttribute(boolean labelAttribute) {
        this.labelAttribute = labelAttribute;
        return this;
    }

    public Attribute setEnumOptions(String enumOptions) {
        this.enumOptions = enumOptions;
        return this;
    }

    public Attribute setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public Attribute setLabel(String label) {
        this.label = label;
        return this;
    }

    public Attribute setDescription(String description) {
        this.description = description;
        return this;
    }

    public Attribute setVisibleExpression(String visibleExpression) {
        this.visibleExpression = visibleExpression;
        return this;
    }

    public Attribute setValidationExpression(String validationExpression) {
        this.validationExpression = validationExpression;
        return this;
    }

    public Attribute setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Attribute setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public Attribute setRangeMin(Integer rangeMin) {
        this.rangeMin = rangeMin;
        return this;
    }

    public Attribute setRangeMax(Integer rangeMax) {
        this.rangeMax = rangeMax;
        return this;
    }

    public Attribute setCompound(Attribute compound) {
        this.compound = compound;
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
        return "Attribute{" + "entityFullname='" + entityFullname + '\'' + ", name='" + name + '\'' + ", dataType="
                + dataType + ", refEntity=" + refEntity + ", idAttribute=" + idAttribute + ", lookupAttribute="
                + lookupAttribute + ", nillable=" + nillable + ", nillableExpression=" + nullableExpression + ", auto=" + auto + ", visible=" + visible + ", readOnly="
                + readOnly + ", unique=" + unique + ", aggregateable=" + aggregateable + ", labelAttribute="
                + labelAttribute + ", enumOptions='" + enumOptions + '\'' + ", expression='" + expression + '\''
                + ", label='" + label + '\'' + ", description='" + description + '\'' + ", visibleExpression='"
                + visibleExpression + '\'' + ", validationExpression='" + validationExpression + '\''
                + ", defaultValue='" + defaultValue + '\'' + ", orderBy='" + orderBy + '\'' + ", mappedBy=" + mappedBy
                + ", rangeMin=" + rangeMin + ", rangeMax=" + rangeMax + ", tags=" + tags + ", labels=" + labels
                + ", descriptions=" + descriptions + ", parts=" + parts.stream()
                .map(Attribute::getName)
                .collect(toList()) + ", compound=" + compound
                + ", entityId='" + entityId + '\'' + '}';
    }

    @Override
    public int compareTo(Attribute attr) {
        return id.compareTo(attr.getId());
    }
}
