package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import java.net.URI;
import java.util.Map;

class MolgenisV1MetadataConverter extends AbstractMetadataConverter
{

	private final WriteableMetadataRepository repository;

	public MolgenisV1MetadataConverter(final WriteableMetadataRepository metadataRepository)
	{
		repository = metadataRepository;
	}

	@Override
	public Tag toTag(final Map<String, String> data)
	{
		Tag tag = repository.createTag(getString(data, "identifier"));
		setString(data, "label", tag::setLabel);
		setData(data, "objectIRI", URI::create, tag::setObjectIRI);
		setData(data, "relationIRI", URI::create, tag::setRelationIRI);
		setString(data, "relationLabel", tag::setRelationLabel);
		setString(data, "codeSystem", tag::setCodeSystem);
		return tag;
	}

	@Override
	public Attribute toAttribute(final Map<String, String> data)
	{
		Attribute att = repository.createAttribute(getString(data, "identifier"));
		setString(data, "name", att::setName);
		setData(data, "dataType", DataType::from, att::setDataType);
		setList(data, "parts", repository::createAttribute, part ->
		{
			part.setCompound(att);
			att.addPart(part);
		});
		setData(data, "refEntity", repository::createEntity, att::setRefEntity);
		setString(data, "expression", att::setExpression);
		setBoolean(data, "nillable", att::setNilleble);
		setBoolean(data, "auto", att::setAuto);
		setBoolean(data, "visible", att::setVisible);
		setString(data, "label", att::setLabel);
		setString(data, "description", att::setDescription);
		setBoolean(data, "aggregateable", att::setAggregateable);
		setString(data, "enumOptions", att::setEnumOptions);
		setInteger(data, "rangeMin", att::setRangeMin);
		setInteger(data, "rangeMax", att::setRangeMax);
		setBoolean(data, "readOnly", att::setReadOnly);
		setBoolean(data, "unique", att::setUnique);
		setList(data, "tags", repository::createTag, att::addTag);
		setString(data, "visibleExpression", att::setVisibleExpression);
		setString(data, "validationExpression", att::setValidationExpression);
		setString(data, "defaultValue", att::setDefaultValue);
		repository.getLanguages()
				  .forEach(lang -> setString(data, "description-" + lang.getCode(),
						  description -> att.addDescription(description, lang)));
		repository.getLanguages()
				  .forEach(lang -> setString(data, "label-" + lang.getCode(), label -> att.addLabel(label, lang)));
		return att;
	}

	@Override
	public Package toPackage(final Map<String, String> data)
	{
		final Package pkg = repository.createPackage(getString(data, "fullName"));
		setString(data, "description", pkg::setDescription);
		setData(data, "parent", repository::createPackage, pkg::setParent);
		setList(data, "tags", repository::createTag, pkg::addTag);
		return pkg;
	}

	@Override
	public Entity toEntity(final Map<String, String> data)
	{
		final Entity ent = repository.createEntity(getString(data, "fullName"));
		setData(data, "backend", Backend::from, ent::setBackend);
		setData(data, "package", repository::createPackage, ent::setPackage);
		setData(data, "idAttribute", repository::createAttribute, att ->
		{
			ent.setIdAttribute(att);
			att.setIdAttribute(true);
		});
		setData(data, "labelAttribute", repository::createAttribute, att ->
		{
			ent.setLabelAttribute(att);
			att.setLabelAttribute(true);
		});
		setList(data, "lookupAttributes", repository::createAttribute, att ->
		{
			ent.addAttribute(att);
			att.setLookupAttribute(true);
		});
		setBoolean(data, "abstract", ent::setAbstractClass);
		setString(data, "label", ent::setLabel);
		setData(data, "extends", repository::createEntity, ent::setBase);
		setString(data, "description", ent::setDescription);
		setList(data, "tags", repository::createTag, ent::addTag);
		setList(data, "attributes", repository::createAttribute, att ->
		{
			ent.addAttribute(att);
			updateReference(ent, att);
		});
		setBoolean(data, "rowLevelSecured", ent::setRowLevelSecured);
		repository.getLanguages()
				  .forEach(lang -> setString(data, "description-" + lang.getCode(),
						  description -> ent.addDescription(description, lang)));
		repository.getLanguages()
				  .forEach(lang -> setString(data, "label-" + lang.getCode(), label -> ent.addLabel(label, lang)));
		return ent;
	}

	@Override
	public Language toLanguage(Map<String, String> data)
	{
		final Language lng = repository.createLanguage(getString(data, "code"));
		setString(data, "name", lng::setName);
		return lng;
	}

	@Override
	public String getTagsRepositoryName()
	{
		return "tags";
	}

	@Override
	public String getPackagesRepositoryName()
	{
		return "packages";
	}

	@Override
	public String getEntitiesRepositoryName()
	{
		return "entities";
	}

	@Override
	public String getAttributesRepositoryName()
	{
		return "attributes";
	}

	@Override
	public String getLanguagesRepositoryName()
	{
		return "languages";
	}
}
