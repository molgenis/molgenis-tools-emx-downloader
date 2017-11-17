package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.util.NameUtils;

import java.util.HashMap;
import java.util.Map;

import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_3;

class MolgenisV3MetadataConverter extends AbstractMetadataConverter
{

	private final WriteableMetadataRepository repository;
	private final MolgenisV2MetadataConverter molgenisV2MetadataConverter;
	private HashMap<String, Entity> entityIdMap;

	public MolgenisV3MetadataConverter(WriteableMetadataRepository metadataRepository)
	{
		repository = metadataRepository;
		molgenisV2MetadataConverter = new MolgenisV2MetadataConverter(repository);

	}

	@Override
	public Entity toEntity(Map<String, String> data)
	{
		String entityId = getString(data, "id");
		String entityName = getString(data, "name");
		final Entity entity = repository.createEntityById(entityId, entityName);
		setData(data, "backend", Backend::from, entity::setBackend);
		setData(data, "package", repository::createPackageById, entity::setPackage);
		setBoolean(data, "isAbstract", entity::setAbstractClass);
		setString(data, "label", entity::setLabel);
		setString(data, "id", entity::setId);
		setData(data, "extends", Entity::createEntityByName, entity::setBase);
		setString(data, "description", entity::setDescription);
		setList(data, "tags", repository::createTag, entity::addTag);
		setList(data, "attributes", repository::createAttribute, att ->
		{
			entity.addAttribute(att);
			updateReference(entity, att);
		});
		repository.getLanguages()
				  .forEach(lang -> setString(data, "description-" + lang.getCode(),
						  description -> entity.addDescription(description, lang)));
		repository.getLanguages()
				  .forEach(lang -> setString(data, "label-" + lang.getCode(), label -> entity.addLabel(label, lang)));
		return entity;
	}

	@Override
	public Package toPackage(Map<String, String> data)
	{
		final Package pkg = repository.createPackage(getString(data, "name"));
		setString(data, "label", pkg::setLabel);
		setString(data, "id", pkg::setId);
		setString(data, "description", pkg::setDescription);
		setData(data, "parent", repository::createPackageById, pkg::setParent);
		setList(data, "tags", repository::createTag, pkg::addTag);

		return pkg;
	}

	@Override
	public Attribute toAttribute(Map<String, String> data)
	{
		final Attribute att = repository.createAttribute(getString(data, "id"));

		setString(data, "name", att::setName);
		setData(data, "type", DataType::from, att::setDataType);
		setString(data, "entity", att::setEntityId);
		setBoolean(data, "isIdAttribute", att::setIdAttribute);
		setBoolean(data, "isLabelAttribute", att::setLabelAttribute);
		setInteger(data, "lookupAttributeIndex", i -> att.setLookupAttribute(true));
		setData(data, "parent", repository::createAttribute, att::setCompound);
		setList(data, "children", repository::createAttribute, part ->
		{
			part.setCompound(att);
			att.addPart(part);
		});
		String refEntityId = getString(data, "refEntityType");
		if (refEntityId != null)
		{
			Entity refEntity = (Entity) new Entity().setId(refEntityId);
			att.setRefEntity(refEntity);
		}
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
	public Tag toTag(Map<String, String> data)
	{
		return molgenisV2MetadataConverter.toTag(data);
	}

	@Override
	public Language toLanguage(Map<String, String> data)
	{
		return molgenisV2MetadataConverter.toLanguage(data);
	}

	@Override
	public String getTagsRepositoryName()
	{
		return molgenisV2MetadataConverter.getTagsRepositoryName();
	}

	@Override
	public String getPackagesRepositoryName()
	{
		return molgenisV2MetadataConverter.getPackagesRepositoryName();
	}

	@Override
	public String getEntitiesRepositoryName()
	{
		return molgenisV2MetadataConverter.getEntitiesRepositoryName();
	}

	@Override
	public String getAttributesRepositoryName()
	{
		return molgenisV2MetadataConverter.getAttributesRepositoryName();
	}

	@Override
	public String getLanguagesRepositoryName()
	{
		return molgenisV2MetadataConverter.getLanguagesRepositoryName();
	}

	@Override
	public void postProcess(WriteableMetadataRepository repository)
	{
		entityIdMap = new HashMap<>();
		Map<String, Package> packageIdMap = new HashMap<>();

		repository.getEntities()
				  .stream()
				  .filter(entity -> entity.getId() != null)
				  .forEach(entity -> entityIdMap.put(entity.getId(), entity));
		repository.getPackages()
				  .stream()
				  .filter(pack -> pack.getId() != null && pack.getName() != null)
				  .forEach(pack -> packageIdMap.put(pack.getId(), pack));

		repository.getPackages()
				  .stream()
				  .filter(pack -> pack.getParent() != null)
				  .forEach(pack -> pack.setParent(packageIdMap.get(pack.getParent().getId())));
		repository.getEntities()
				  .stream()
				  .filter(entity -> entity.getPackage() != null)
				  .forEach(entity -> entity.setPackage(packageIdMap.get(entity.getPackage().getId())));
		repository.getEntities()
				  .stream()
				  .filter(entity -> entity.getBase() != null)
				  .forEach(entity -> entity.setBase(entityIdMap.get(entity.getBase().getId())));
		repository.getEntities()
				  .stream()
				  .filter(entity -> entity.getPackage() != null)
				  .forEach(entity -> entity.setFullName(
						  NameUtils.getPackageFullName(entity.getPackage(), VERSION_3) + "_" + entity.getFullName()));
		repository.getAttributes()
				  .stream()
				  .filter(attribute -> attribute.getEntityId() != null)
				  .forEach(attribute -> test(attribute));
		repository.getAttributes()
				  .stream()
				  .filter(attribute -> attribute.getRefEntity() != null)
				  .forEach(attribute -> attribute.setRefEntity(entityIdMap.get(attribute.getRefEntity().getId())));
	}

	private void test(Attribute attribute)
	{
		try
		{
			attribute.setEntityFullname(entityIdMap.get(attribute.getEntityId()).getFullName());
		}
		catch (Exception e)
		{
			throw new RuntimeException("");
		}
	}
}
