package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.MetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import java.util.*;
import java.util.stream.Collectors;

import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_3;

class FilteredMetadataRepository implements MetadataRepository
{

	private final Set<Entity> entities;
	private final Set<Attribute> attributes;
	private final Set<Package> packages;
	private final Set<Tag> tags;
	private final Collection<Language> languages;

	public FilteredMetadataRepository(final MetadataRepository source, final List<String> entities,
			MolgenisVersion version)
	{
		this.entities = new LinkedHashSet<>();
		attributes = new LinkedHashSet<>();
		packages = new LinkedHashSet<>();
		tags = new LinkedHashSet<>();
		languages = source.getLanguages();
		if (version.smallerThan(VERSION_3))
		{
			source.getEntities().stream().filter((ent) -> entities.contains(ent.getFullName())).forEach(this::traverse);
		}
		else
		{
			source.getEntities()
				  .stream()
				  .filter(entity -> entities.contains(entity.getFullName()))
				  .forEach(this::traverse);
		}
	}

	private void traverse(final Entity entity)
	{
		if (entity != null && !entities.contains(entity))
		{
			entities.add(entity);
			tags.addAll(entity.getTags());
			traverse(entity.getPackage());
			traverse(entity.getBase());
			entity.getAttributes().forEach(this::traverse);
		}
	}

	private void traverse(final Package pkg)
	{
		if (pkg != null && !packages.contains(pkg))
		{
			packages.add(pkg);
			tags.addAll(pkg.getTags());
			traverse(pkg.getParent());
		}
	}

	private void traverse(final Attribute att)
	{
		if (att != null && !attributes.contains(att))
		{
			attributes.add(att);
			tags.addAll(att.getTags());
			att.getParts().forEach(this::traverse);
			traverse(att.getRefEntity());
		}
	}

	@Override
	public final Collection<Attribute> getAttributes()
	{
		return attributes.stream()
						 .sorted(Comparator.comparing(Attribute::getEntityFullname))
						 .collect(Collectors.toList());
	}

	@Override
	public final Collection<Entity> getEntities()
	{
		return entities.stream().sorted((x, y) ->
		{
			if (x.isParentOf(y))
			{
				return -1;
			}
			if (y.isParentOf(x))
			{
				return 1;
			}
			if (x.isAbstractClass() != y.isAbstractClass())
			{
				return x.isAbstractClass() ? -1 : 1;
			}
			return x.getFullName().compareTo(y.getFullName());
		}).collect(Collectors.toList());
	}

	@Override
	public final Collection<Package> getPackages()
	{
		return packages.stream().sorted(Comparator.comparing(Package::getName)).collect(Collectors.toList());
	}

	@Override
	public final Collection<Tag> getTags()
	{
		return tags.stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toSet());
	}

	@Override
	public Collection<Language> getLanguages()
	{
		return languages;
	}
}
