package org.molgenis.downloader.api.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public final class Attribute implements Metadata {
    private String entityFullname;
    private String id;
    private String name;
    private DataType dataType;
    private Entity refEntity;
    private boolean idAttribute;
    private boolean lookupAttribute;
    private boolean nillable;
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
    private Integer rangeMin;
    private Integer rangeMax;
    private final Set<Tag> tags;
    private final Map<Language, String> labels;
    private final Map<Language, String> descriptions;
    private final List<Attribute> parts;
    private Attribute compound;

    public Attribute(final String id, final String name) {
        this.id = id;
        this.name = name;
        labels = new HashMap<>();
        descriptions = new HashMap<>();
        tags = new HashSet<>();
        parts = new ArrayList<>();        
        visible = true;
        dataType = DataType.STRING;
    }
    //FIXME: can we do this cleaner?
    public Attribute(final String id) {
       this(id, null);
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
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (idAttribute != attribute.idAttribute) return false;
        if (lookupAttribute != attribute.lookupAttribute) return false;
        if (nillable != attribute.nillable) return false;
        if (auto != attribute.auto) return false;
        if (visible != attribute.visible) return false;
        if (readOnly != attribute.readOnly) return false;
        if (unique != attribute.unique) return false;
        if (aggregateable != attribute.aggregateable) return false;
        if (labelAttribute != attribute.labelAttribute) return false;
        if (entityFullname != null ? !entityFullname.equals(attribute.entityFullname) : attribute.entityFullname != null) return false;
        if (id != null ? !id.equals(attribute.id) : attribute.id != null) return false;
        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        if (dataType != attribute.dataType) return false;
        if (refEntity != null ? !refEntity.equals(attribute.refEntity) : attribute.refEntity != null) return false;
        if (enumOptions != null ? !enumOptions.equals(attribute.enumOptions) : attribute.enumOptions != null)
            return false;
        if (expression != null ? !expression.equals(attribute.expression) : attribute.expression != null) return false;
        if (label != null ? !label.equals(attribute.label) : attribute.label != null) return false;
        if (description != null ? !description.equals(attribute.description) : attribute.description != null)
            return false;
        if (visibleExpression != null ? !visibleExpression.equals(attribute.visibleExpression) :
                attribute.visibleExpression != null) return false;
        if (validationExpression != null ? !validationExpression.equals(attribute.validationExpression) :
                attribute.validationExpression != null) return false;
        if (defaultValue != null ? !defaultValue.equals(attribute.defaultValue) : attribute.defaultValue != null)
            return false;
        if (orderBy != null ? !orderBy.equals(attribute.orderBy) : attribute.orderBy != null) return false;
        if (mappedBy != null ? !mappedBy.equals(attribute.mappedBy) : attribute.mappedBy != null) return false;
        if (rangeMin != null ? !rangeMin.equals(attribute.rangeMin) : attribute.rangeMin != null) return false;
        if (rangeMax != null ? !rangeMax.equals(attribute.rangeMax) : attribute.rangeMax != null) return false;
        if (tags != null ? !tags.equals(attribute.tags) : attribute.tags != null) return false;
        if (labels != null ? !labels.equals(attribute.labels) : attribute.labels != null) return false;
        if (descriptions != null ? !descriptions.equals(attribute.descriptions) : attribute.descriptions != null)
            return false;
        if (parts != null ? !parts.equals(attribute.parts) : attribute.parts != null) return false;
        return compound != null ? compound.equals(attribute.compound) : attribute.compound == null;
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

    public String getId() {
        return id;
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

    public String getEnumOptions() {
        return enumOptions;
    }

    public void setEntityFullname(String entityFullname) {
        this.entityFullname = entityFullname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public void setRefEntity(Entity refEntity) {
        this.refEntity = refEntity;
    }

    public void setIdAttribute(boolean idAttribute) {
        this.idAttribute = idAttribute;
    }

    public void setLookupAttribute(boolean lookupAttribute) {
        this.lookupAttribute = lookupAttribute;
    }

    public void setNilleble(boolean nillable) {
        this.nillable = nillable;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public void setAggregateable(boolean aggregateable) {
        this.aggregateable = aggregateable;
    }

    public void setLabelAttribute(boolean labelAttribute) {
        this.labelAttribute = labelAttribute;
    }

    public void setEnumOptions(String enumOptions) {
        this.enumOptions = enumOptions;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVisibleExpression(String visibleExpression) {
        this.visibleExpression = visibleExpression;
    }

    public void setValidationExpression(String validationExpression) {
        this.validationExpression = validationExpression;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setRangeMin(Integer rangeMin) {
        this.rangeMin = rangeMin;
    }

    public void setRangeMax(Integer rangeMax) {
        this.rangeMax = rangeMax;
    }

    public void setCompound(Attribute compound) {
        this.compound = compound;
    }
    
    public void addPart(final Attribute part) {
        parts.add(part);
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
    public String toString()
    {
        return "Attribute{" + "entityFullname='" + entityFullname + '\'' + ", id='" + id + '\'' + ", name='" + name + '\''
                + ", dataType=" + dataType + ", refEntity=" + refEntity + ", idAttribute=" + idAttribute
                + ", lookupAttribute=" + lookupAttribute + ", nillable=" + nillable + ", auto=" + auto + ", visible="
                + visible + ", readOnly=" + readOnly + ", unique=" + unique + ", aggregateable=" + aggregateable
                + ", labelAttribute=" + labelAttribute + ", enumOptions='" + enumOptions + '\'' + ", expression='"
                + expression + '\'' + ", label='" + label + '\'' + ", description='" + description + '\''
                + ", visibleExpression='" + visibleExpression + '\'' + ", validationExpression='" + validationExpression
                + '\'' + ", defaultValue='" + defaultValue + '\'' + ", orderBy='" + orderBy + '\'' + ", mappedBy="
                + mappedBy + ", rangeMin=" + rangeMin + ", rangeMax=" + rangeMax + ", tags=" + tags + ", labels="
                + labels + ", descriptions=" + descriptions + ", parts=" + parts + ", compound=" + compound + '}';
    }
}
