package org.molgenis.downloader.emx.serializers;

import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.*;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class EMXAttributeSerializer implements EntitySerializer<Attribute>
{

	private static final String[] FIELDS = { "entity", "name", "label", "dataType", "refEntity", "nillable",
			"idAttribute", "enumOptions", "defaultValue", "rangeMin", "rangeMax", "lookupAttribute", "labelAttribute",
			"readOnly", "aggregateable", "visible", "unique", "partOfAttribute", "expression", "validationExpression",
			"tags", "description", };

	private static final MolgenisVersion MIN_VERSION_FOR_MAPPEDBY = new MolgenisVersion(2, 0, 0);
	private static final String AUTO = "AUTO";
	private static final String MAPPED_BY = "mappedBy";

	private final MolgenisVersion version;
	private final Collection<Language> languages;

	public EMXAttributeSerializer(final MolgenisVersion molgenisVersion, final Collection<Language> languages)
	{
		version = molgenisVersion;
		this.languages = languages;
	}

	@Override
	public List<String> serialize(final Attribute att)
	{
		List<String> result = new ArrayList<>();
		final String entity = att.getEntityFullname();
		result.add(entity);
		result.add(att.getName());
		result.add(att.getLabel());
		result.add(att.getDataType().name().toLowerCase());
		result.add(Optional.ofNullable(att.getRefEntity()).map(Entity::getFullName).orElse(""));
		result.add(Boolean.toString(att.isNillable()));
		final boolean isAutoIdAttriubte = att.isAuto();
		final boolean isIdAttribute = att.isIdAttribute();
		if (isIdAttribute && isAutoIdAttriubte)
		{
			result.add(AUTO);
		}
		else
		{
			result.add(Boolean.toString(isIdAttribute));
		}
		result.add(att.getEnumOptions());
		result.add(att.getDefaultValue());
		if (att.getDataType().isNumericType() && att.getRangeMin() != null)
		{
			result.add(Long.toString(att.getRangeMin()));

		}
		else
		{
			result.add(null);
		}
		if (att.getDataType().isNumericType() && att.getRangeMax() != null)
		{
			result.add(Long.toString(att.getRangeMax()));
		}
		else
		{
			result.add(null);
		}
		result.add(Boolean.toString(att.isLookupAttribute()));
		result.add(Boolean.toString(att.isLabelAttribute()));
		result.add(Boolean.toString(att.isReadOnly()));
		result.add(Boolean.toString(att.isAggregateable()));
		result.add(Boolean.toString(att.isVisible()));
		result.add(Boolean.toString(att.isUnique()));
		result.add(Optional.ofNullable(att.getCompound()).map(Attribute::getName).orElse(""));
		result.add(att.getExpression());
		result.add(att.getValidationExpression());
		result.add(att.getTags().stream().map(Tag::getId).collect(joining(",")));
		result.add(att.getDescription());
		if (fields().contains(MAPPED_BY))
		{
			result.add(att.getMappedBy() != null ? att.getMappedBy().getName() : "");
		}
		languages.forEach(language ->
		{
			result.add(att.getDescriptions().get(language));
			result.add(att.getLabels().get(language));
		});
		return result;
	}

	@Override
	public List<String> fields()
	{
		final List<String> fields = new ArrayList<>(Arrays.asList(FIELDS));
		if (version.equalsOrLargerThan(MIN_VERSION_FOR_MAPPEDBY))
		{
			fields.add(MAPPED_BY);
		}

		languages.forEach(language ->
		{
			fields.add("description-" + language.getCode());
			fields.add("label-" + language.getCode());
		});
		return fields;
	}

}
