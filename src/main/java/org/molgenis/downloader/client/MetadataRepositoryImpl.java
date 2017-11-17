package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetadataRepositoryImpl implements WriteableMetadataRepository
{

	private final Map<String, Tag> tags;
	private final Map<String, Package> packages;
	private final Map<String, Entity> entities;
	private final Map<String, Attribute> attributes;
	private final Map<String, Language> languages;

	public MetadataRepositoryImpl()
	{
		tags = new LinkedHashMap<>();
		packages = new LinkedHashMap<>();
		entities = new LinkedHashMap<>();
		attributes = new LinkedHashMap<>();
		languages = new LinkedHashMap<>();
	}

	@Override
	public Collection<Tag> getTags()
	{
		return tags.values();
	}

	@Override
	public Collection<Package> getPackages()
	{
		return packages.values();
	}

	@Override
	public Collection<Entity> getEntities()
	{
		return entities.values();
	}

	@Override
	public Collection<Attribute> getAttributes()
	{
		return attributes.values();
	}

	@Override
	public Collection<Language> getLanguages()
	{
		return languages.values();
	}

	@Override
	public Language createLanguage(final String code)
	{
		return languages.computeIfAbsent(code, Language::from);
	}

	@Override
	public Tag createTag(final String identifier)
	{
		return tags.computeIfAbsent(identifier, Tag::from);
	}

	@Override
	public Package createPackage(final String fullName)
	{
		return packages.computeIfAbsent(fullName, Package::from);
	}

	@Override
	public Package createPackageById(final String id)
	{
		return Package.fromId(id);
	}

	@Override
	public Entity createEntity(final String fullName)
	{
		return entities.computeIfAbsent(fullName, Entity::createEntityByName);
	}

	@Override
	public Entity createEntityById(String id, String name)
	{
		if (entities.containsKey(id))
		{
			return entities.get(id);
		}
		else
		{
			Entity entity = Entity.createEntityByName(name);
			entity.setId(id);
			entities.put(id, entity);
			return entity;
		}
	}

	@Override
	public Entity createEntity(final String name, final String pkgName)
	{
		final String fullName = pkgName + "_" + name;
		return entities.computeIfAbsent(fullName, Entity::createEntityByName);
	}

	@Override
	public Attribute createAttribute(final String identifier)
	{
		return attributes.computeIfAbsent(identifier, Attribute::from);
	}
}
