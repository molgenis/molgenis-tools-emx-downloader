package org.molgenis.downloader.rdf;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.metadata.Entity;

@FunctionalInterface
public interface EntityConsumerFactory
{
	EntityConsumer create(Entity entity, RepositoryConnection connection, RdfConfig rdfConfig);
}
