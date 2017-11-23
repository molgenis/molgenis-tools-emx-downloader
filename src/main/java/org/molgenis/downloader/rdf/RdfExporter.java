package org.molgenis.downloader.rdf;

import org.molgenis.downloader.api.MetadataRepository;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.client.IncompleteMetadataException;
import org.molgenis.rdf.RdfTemplate;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Streaming export of entity data from MolgenisClient to RDF format.
 */
public class RdfExporter
{
	/**
	 * The MolgenisClient to retrieve entity data
	 */
	private MolgenisClient molgenisClient;
	/**
	 * The RdfTemplate to use to obtain connections to the RDF repository.
	 */
	private RdfTemplate template;
	/**
	 * Function to use to create an EntityConsumer.
	 */
	private EntityConsumerFactory consumerFactory;
	/**
	 * Contains the configuration.
	 */
	private RdfConfig rdfConfig;

	public RdfExporter(MolgenisClient molgenisClient, RdfTemplate template, RdfConfig config)
	{
		this(molgenisClient, template, RdfEntityConsumer::create, config);
	}

	RdfExporter(MolgenisClient molgenisClient, RdfTemplate template, EntityConsumerFactory consumerFactory,
			RdfConfig rdfConfig)
	{
		this.molgenisClient = requireNonNull(molgenisClient);
		this.template = requireNonNull(template);
		this.consumerFactory = requireNonNull(consumerFactory);
		this.rdfConfig = requireNonNull(rdfConfig);
	}

	void exportData(List<String> entities, Integer pageSize, MolgenisVersion molgenisVersion)
			throws IncompleteMetadataException
	{
		addNamespaces();
		MetadataRepository metadata = molgenisClient.getMetadata(molgenisVersion);
		for (String fullName : entities)
		{
			exportEntity(metadata.getEntity(fullName), pageSize);
		}
	}
	
	private void addNamespaces()
	{
		template.execute(connection -> rdfConfig.getNamespaces().forEach(connection::setNamespace));
	}

	void exportEntity(Entity entity, Integer pageSize)
	{
		template.execute(connection -> molgenisClient.streamEntityData(entity.getFullName(),
				consumerFactory.create(entity, connection, rdfConfig), pageSize));
	}
}
