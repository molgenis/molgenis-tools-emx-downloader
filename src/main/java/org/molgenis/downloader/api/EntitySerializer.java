package org.molgenis.downloader.api;

import java.util.List;
import org.molgenis.downloader.api.metadata.Metadata;

public interface EntitySerializer<T extends Metadata> {
  List<String> serialize(T entity);

  List<String> fields();
}
