package org.molgenis.rdf;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public interface ConnectionCallback
{
	void doInConnection(RepositoryConnection con) throws RDF4JException;
}
