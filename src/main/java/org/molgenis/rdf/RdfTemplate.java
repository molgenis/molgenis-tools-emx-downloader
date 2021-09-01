package org.molgenis.rdf;

import java.util.Objects;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public class RdfTemplate {
  private final Repository repository;

  public RdfTemplate(Repository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  public void execute(ConnectionCallback callback) {
    try (RepositoryConnection connection = repository.getConnection()) {
      callback.doInConnection(connection);
    }
  }
}
