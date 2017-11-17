package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import java.net.URI;
import java.util.Map;

class MolgenisV4MetadataConverter extends AbstractMetadataConverter
{

	private final WriteableMetadataRepository repository;

	public MolgenisV4MetadataConverter(WriteableMetadataRepository metadataRepository)
	{
		repository = metadataRepository;
	}

	@Override
	public Attribute toAttribute(Map<String, String> data)
	{
		final Attribute att = repository.createAttribute(getString(data, "id"));
		setString(data, "name", att::setName);
		setData(data, "type", DataType::from, att::setDataType);
		setString(data, "entity", att::setEntityFullname);
		setBoolean(data, "isIdAttribute", att::setIdAttribute);
		setBoolean(data, "isLabelAttribute", att::setLabelAttribute);
		setInteger(data, "lookupAttributeIndex", i -> att.setLookupAttribute(true));
		setData(data, "parent", repository::createAttribute, att::setCompound);
		setList(data, "children", repository::createAttribute, part ->
		{
			part.setCompound(att);
			att.addPart(part);
		});
		setData(data, "refEntityType", repository::createEntity, att::setRefEntity);
		setData(data, "mappedBy", repository::createAttribute, att::setMappedBy);
		setString(data, "orderBy", att::setOrderBy);
		setString(data, "expression", att::setExpression);
		setBoolean(data, "isNullable", att::setNilleble);
		setBoolean(data, "isVisible", att::setVisible);
		setString(data, "label", att::setLabel);
		setString(data, "description", att::setDescription);
		setBoolean(data, "isAggregatable", att::setAggregateable);
		setString(data, "enumOptions", att::setEnumOptions);
		setInteger(data, "rangeMin", att::setRangeMin);
		setInteger(data, "rangeMax", att::setRangeMax);
		setBoolean(data, "isReadOnly", att::setReadOnly);
		setBoolean(data, "isUnique", att::setUnique);
		setBoolean(data, "isAuto", att::setAuto);
		setList(data, "tags", repository::createTag, att::addTag);
		setString(data, "visibleExpression", att::setVisibleExpression);
		setString(data, "validationExpression", att::setValidationExpression);
		setString(data, "defaultValue", att::setDefaultValue);

		// IGNORED: sequenceNr
		return att;
	}

	@Override
	public Entity toEntity(Map<String, String> data)
	{
		final Entity ent = repository.createEntity(getString(data, "id"));
		setData(data, "backend", Backend::from, ent::setBackend);
		setData(data, "package", repository::createPackage, ent::setPackage);
		setBoolean(data, "isAbstract", ent::setAbstractClass);
		setString(data, "label", ent::setLabel);
		setData(data, "extends", repository::createEntity, ent::setBase);
		setString(data, "description", ent::setDescription);
		setList(data, "tags", repository::createTag, ent::addTag);
		setList(data, "attributes", repository::createAttribute, att ->
		{
			ent.addAttribute(att);
			updateReference(ent, att);
		});
		repository.getLanguages()
				  .forEach(lang -> setString(data, "description-" + lang.getCode(),
						  description -> ent.addDescription(description, lang)));
		repository.getLanguages()
				  .forEach(lang -> setString(data, "label-" + lang.getCode(), label -> ent.addLabel(label, lang)));
		return ent;
	}

	@Override
	public Package toPackage(Map<String, String> data)
	{
		final Package pkg = repository.createPackage(getString(data, "id"));
		setString(data, "label", pkg::setLabel);
		setString(data, "description", pkg::setDescription);
		setData(data, "parent", repository::createPackage, pkg::setParent);
		setList(data, "tags", repository::createTag, pkg::addTag);
		return pkg;
	}

	@Override
	public Tag toTag(Map<String, String> data)
	{
		final Tag tag = repository.createTag(getString(data, "id"));
		setString(data, "label", tag::setLabel);
		setData(data, "objectIRI", URI::create, tag::setObjectIRI);
		setData(data, "relationIRI", URI::create, tag::setRelationIRI);
		setString(data, "relationLabel", tag::setRelationLabel);
		setString(data, "codeSystem", tag::setCodeSystem);
		return tag;
	}

	@Override
	public Language toLanguage(Map<String, String> data)
	{
		final Language lng = repository.createLanguage(getString(data, "code"));
		setString(data, "name", lng::setName);
		setBoolean(data, "active", lng::setActive);
		return lng;
	}

	@Override
	public String getTagsRepositoryName()
	{
		return "sys_md_Tag";
	}

	@Override
	public String getPackagesRepositoryName()
	{
		return "sys_md_Package";
	}

	@Override
	public String getEntitiesRepositoryName()
	{
		return "sys_md_EntityType";
	}

	@Override
	public String getAttributesRepositoryName()
	{
		return "sys_md_Attribute";
	}

	@Override
	public String getLanguagesRepositoryName()
	{
		return "sys_Language";
	}
}
