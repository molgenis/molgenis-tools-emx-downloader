package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.MetadataRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.client.MolgenisRestApiClient;

class MetadataFilter implements MetadataConsumer
{

	private final MetadataConsumer consumer;
	private final List<String> entities;
	private MetadataRepository target;
	private MolgenisVersion version;

	public MetadataFilter(final List<String> entities, final MetadataConsumer consumer, MolgenisVersion version)
	{
		this.entities = entities;
		this.consumer = consumer;
		this.version = version;
	}

	@Override
	public void accept(final MetadataRepository source)
	{
		target = new FilteredMetadataRepository(source, entities, version);
		consumer.accept(target);
	}

	public List<String> getIncludedEntities()
	{
		if (version.smallerThan(MolgenisRestApiClient.VERSION_3))
		{
			return target.getEntities().stream().filter(ent -> !entities.contains(ent.getFullName()))
					.filter(ent -> !ent.isAbstractClass()).map(Entity::getFullName).collect(toList());
		}
		else
		{
			return target.getEntities().stream().filter(ent -> !entities.contains(ent.getFullName()))
					.filter(ent -> !ent.isAbstractClass()).map(entity ->
							entity.getPackage() == null ? entity.getFullName() :
									entity.getPackage().getFullName() + "_" + entity.getFullName()).collect(toList());
		}
	}
}
