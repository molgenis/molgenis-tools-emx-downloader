package org.molgenis.downloader.emx;

import static java.util.stream.Collectors.toList;
import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_3;

import java.util.List;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.MetadataRepository;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;

class MetadataFilter implements MetadataConsumer {

  private final MetadataConsumer consumer;
  private final List<String> entities;
  private final MolgenisVersion version;
  private MetadataRepository target;

  public MetadataFilter(
      final List<String> entities, final MetadataConsumer consumer, MolgenisVersion version) {
    this.entities = entities;
    this.consumer = consumer;
    this.version = version;
  }

  @Override
  public void accept(final MetadataRepository source) {
    target = new FilteredMetadataRepository(source, entities);
    consumer.accept(target);
  }

  public List<String> getIncludedEntities() {
    if (version.smallerThan(VERSION_3)) {
      return target.getEntities().stream()
          .filter(ent -> !entities.contains(ent.getFullName()))
          .filter(ent -> !ent.isAbstractClass())
          .map(Entity::getFullName)
          .collect(toList());
    } else {
      return target.getEntities().stream()
          .filter(ent -> !entities.contains(ent.getId()))
          .filter(ent -> !ent.isAbstractClass())
          .map(entity -> entity.getFullName())
          .collect(toList());
    }
  }
}
