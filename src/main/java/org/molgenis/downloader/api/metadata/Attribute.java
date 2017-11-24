package org.molgenis.downloader.api.metadata;

import java.util.*;

import static java.util.stream.Collectors.toList;

public final class Attribute extends Metadata implements Comparable<Attribute>
{
	private String entityFullname;
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
	private String entityId;

	public Attribute(final String id)
	{
		super.setId(id);
		labels = new HashMap<>();
		descriptions = new HashMap<>();
		tags = new HashSet<>();
		parts = new ArrayList<>();
		visible = true;
		dataType = DataType.STRING;
	}

	public static Attribute createAttribute(final String id, final String name)
	{
		return new Attribute(id).setName(name);
	}

	public static Attribute from(final String id)
	{
		if (id == null || id.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		final Attribute att = new Attribute(id);
		att.setName(id);
		return att;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Attribute)) return false;

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
		if (entityFullname != null ? !entityFullname.equals(attribute.entityFullname) :
				attribute.entityFullname != null) return false;
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
		if (compound != null ? !compound.equals(attribute.compound) : attribute.compound != null) return false;
		return entityId != null ? entityId.equals(attribute.entityId) : attribute.entityId == null;
	}

	@Override
	public int hashCode()
	{
		int result = entityFullname != null ? entityFullname.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
		result = 31 * result + (refEntity != null ? refEntity.hashCode() : 0);
		result = 31 * result + (idAttribute ? 1 : 0);
		result = 31 * result + (lookupAttribute ? 1 : 0);
		result = 31 * result + (nillable ? 1 : 0);
		result = 31 * result + (auto ? 1 : 0);
		result = 31 * result + (visible ? 1 : 0);
		result = 31 * result + (readOnly ? 1 : 0);
		result = 31 * result + (unique ? 1 : 0);
		result = 31 * result + (aggregateable ? 1 : 0);
		result = 31 * result + (labelAttribute ? 1 : 0);
		result = 31 * result + (enumOptions != null ? enumOptions.hashCode() : 0);
		result = 31 * result + (expression != null ? expression.hashCode() : 0);
		result = 31 * result + (label != null ? label.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (visibleExpression != null ? visibleExpression.hashCode() : 0);
		result = 31 * result + (validationExpression != null ? validationExpression.hashCode() : 0);
		result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
		result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
		result = 31 * result + (mappedBy != null ? mappedBy.hashCode() : 0);
		result = 31 * result + (rangeMin != null ? rangeMin.hashCode() : 0);
		result = 31 * result + (rangeMax != null ? rangeMax.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		result = 31 * result + (labels != null ? labels.hashCode() : 0);
		result = 31 * result + (descriptions != null ? descriptions.hashCode() : 0);
		result = 31 * result + (compound != null ? compound.hashCode() : 0);
		result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
		return result;
	}

	public Attribute getMappedBy()
	{
		return mappedBy;
	}

	public void setMappedBy(Attribute mappedBy)
	{
		this.mappedBy = mappedBy;
	}

	public String getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public boolean isLabelAttribute()
	{
		return labelAttribute;
	}

	public List<Attribute> getParts()
	{
		return parts;
	}

	public Attribute getCompound()
	{
		return compound;
	}

	public String getEntityFullname()
	{
		return entityFullname;
	}

	public Entity getRefEntity()
	{
		return refEntity;
	}

	public boolean isIdAttribute()
	{
		return idAttribute;
	}

	public boolean isLookupAttribute()
	{
		return lookupAttribute;
	}

	public String getExpression()
	{
		return expression;
	}

	public boolean isAuto()
	{
		return auto;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public String getLabel()
	{
		return label;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean isAggregateable()
	{
		return aggregateable;
	}

	public Integer getRangeMin()
	{
		return rangeMin;
	}

	public Integer getRangeMax()
	{
		return rangeMax;
	}

	public boolean isReadOnly()
	{
		return readOnly;
	}

	public boolean isUnique()
	{
		return unique;
	}

	public Set<Tag> getTags()
	{
		return tags;
	}

	public String getVisibleExpression()
	{
		return visibleExpression;
	}

	public String getValidationExpression()
	{
		return validationExpression;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public Map<Language, String> getLabels()
	{
		return labels;
	}

	public Map<Language, String> getDescriptions()
	{
		return descriptions;
	}

	public String getName()
	{
		return name;
	}

	public DataType getDataType()
	{
		return dataType;
	}

	public boolean isNillable()
	{
		return nillable;
	}

	public String getEnumOptions()
	{
		return enumOptions;
	}

	public Attribute setEntityFullname(String entityFullname)
	{
		this.entityFullname = entityFullname;
		return this;
	}

	public Attribute setEntityId(String id)
	{
		this.entityId = id;
		return this;
	}

	public String getEntityId()
	{
		return this.entityId;
	}

	public Attribute setName(String name)
	{
		this.name = name;
		return this;
	}

	public Attribute setDataType(DataType dataType)
	{
		this.dataType = dataType;
		return this;
	}

	public Attribute setRefEntity(Entity refEntity)
	{
		this.refEntity = refEntity;
		return this;
	}

	public Attribute setIdAttribute(boolean idAttribute)
	{
		this.idAttribute = idAttribute;
		return this;
	}

	public Attribute setLookupAttribute(boolean lookupAttribute)
	{
		this.lookupAttribute = lookupAttribute;
		return this;
	}

	public Attribute setNilleble(boolean nillable)
	{
		this.nillable = nillable;
		return this;
	}

	public Attribute setAuto(boolean auto)
	{
		this.auto = auto;
		return this;
	}

	public Attribute setVisible(boolean visible)
	{
		this.visible = visible;
		return this;
	}

	public Attribute setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
		return this;
	}

	public Attribute setUnique(boolean unique)
	{
		this.unique = unique;
		return this;
	}

	public Attribute setAggregateable(boolean aggregateable)
	{
		this.aggregateable = aggregateable;
		return this;
	}

	public Attribute setLabelAttribute(boolean labelAttribute)
	{
		this.labelAttribute = labelAttribute;
		return this;
	}

	public Attribute setEnumOptions(String enumOptions)
	{
		this.enumOptions = enumOptions;
		return this;
	}

	public Attribute setExpression(String expression)
	{
		this.expression = expression;
		return this;
	}

	public Attribute setLabel(String label)
	{
		this.label = label;
		return this;
	}

	public Attribute setDescription(String description)
	{
		this.description = description;
		return this;
	}

	public Attribute setVisibleExpression(String visibleExpression)
	{
		this.visibleExpression = visibleExpression;
		return this;
	}

	public Attribute setValidationExpression(String validationExpression)
	{
		this.validationExpression = validationExpression;
		return this;
	}

	public Attribute setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
		return this;
	}

	public Attribute setRangeMin(Integer rangeMin)
	{
		this.rangeMin = rangeMin;
		return this;
	}

	public Attribute setRangeMax(Integer rangeMax)
	{
		this.rangeMax = rangeMax;
		return this;
	}

	public Attribute setCompound(Attribute compound)
	{
		this.compound = compound;
		return this;
	}

	public Attribute addPart(final Attribute part)
	{
		parts.add(part);
		return this;
	}

	public Attribute addTag(final Tag tag)
	{
		tags.add(tag);
		return this;
	}

	public Attribute addDescription(final String description, final Language language)
	{
		descriptions.put(language, description);
		return this;
	}

	public void addLabel(final String label, final Language language)
	{
		labels.put(language, label);
	}

	@Override
	public String toString()
	{
		return "Attribute{" + "entityFullname='" + entityFullname + '\'' + ", name='" + name + '\'' + ", dataType="
				+ dataType + ", refEntity=" + refEntity + ", idAttribute=" + idAttribute + ", lookupAttribute="
				+ lookupAttribute + ", nillable=" + nillable + ", auto=" + auto + ", visible=" + visible + ", readOnly="
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
	public int compareTo(Attribute attr)
	{
		return id.compareTo(attr.getId());
	}
}
