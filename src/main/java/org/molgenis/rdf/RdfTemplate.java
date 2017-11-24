package org.molgenis.rdf;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.Objects;

public class RdfTemplate
{
	private Repository repository;

	public RdfTemplate(Repository repository)
	{
		this.repository = Objects.requireNonNull(repository);
	}

	public void execute(ConnectionCallback callback)
	{
		try (RepositoryConnection connection = repository.getConnection())
		{
			callback.doInConnection(connection);
		}
	}
}

