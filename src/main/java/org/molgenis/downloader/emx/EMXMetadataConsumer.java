package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.*;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.emx.serializers.EMXAttributeSerializer;
import org.molgenis.downloader.emx.serializers.EMXEntitySerializer;
import org.molgenis.downloader.emx.serializers.EMXPackageSerializer;
import org.molgenis.downloader.emx.serializers.EMXTagSerializer;
import org.molgenis.downloader.emx.serializers.v3.EMXAttributeSerializerV3;
import org.molgenis.downloader.emx.serializers.v3.EMXEntitySerializerV3;
import org.molgenis.downloader.emx.serializers.v3.EMXPackageSerializerV3;

import java.io.IOException;
import java.util.Collection;

import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_2;

class EMXMetadataConsumer implements MetadataConsumer
{

	private static final String ATTRIBUTES = "attributes";
	private static final String ENTITIES = "entities";
	private static final String PACKAGES = "packages";
	private static final String TAGS = "tags";
	private final MolgenisVersion version;
	private final EMXWriter writer;

	public EMXMetadataConsumer(EMXWriter writer, MolgenisVersion molgenisVersion)
	{
		version = molgenisVersion;
		this.writer = writer;
	}

	@Override
	public void accept(final MetadataRepository repository)
	{
		try
		{
			EntitySerializer<Attribute> attributesSerializer;
			EntitySerializer<Package> packagesSerializer;
			EntitySerializer<Entity> entitiesSerializer;
			if (version.equalsOrSmallerThan(VERSION_2))
			{
				attributesSerializer = new EMXAttributeSerializer(version, repository.getLanguages());
				packagesSerializer = new EMXPackageSerializer();
				entitiesSerializer = new EMXEntitySerializer(repository.getLanguages());
			}
			else
			{
				attributesSerializer = new EMXAttributeSerializerV3(repository.getLanguages());
				packagesSerializer = new EMXPackageSerializerV3(version);
				entitiesSerializer = new EMXEntitySerializerV3(version, repository.getLanguages());
			}
			writeMetadata(ATTRIBUTES, attributesSerializer, repository.getAttributes());
			writeMetadata(ENTITIES, entitiesSerializer, repository.getEntities());
			writeMetadata(PACKAGES, packagesSerializer, repository.getPackages());

			EMXTagSerializer tagSerializer = new EMXTagSerializer();
			writeMetadata(TAGS, tagSerializer, repository.getTags());
		}
		catch (final IOException ex)
		{
			writer.addException(ex);
		}
	}

	private <M extends Metadata> void writeMetadata(final String name, EntitySerializer serializer,
			final Collection<M> metadata) throws IOException
	{
		EMXDataStore sheet = writer.createDataStore(name);
		sheet.writeRow(serializer.fields());
		for (final M m : metadata)
		{
			sheet.writeRow(serializer.serialize(m));
		}
	}
}
