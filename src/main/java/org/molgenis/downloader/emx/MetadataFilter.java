
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.MetadataRepository;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.metadata.Entity;


class MetadataFilter implements MetadataConsumer {

    private final MetadataConsumer consumer;
    private final List<String> entities;
    private MetadataRepository target;

    public MetadataFilter(final List<String> entities, final MetadataConsumer consumer) {
        this.entities = entities;
        this.consumer = consumer;
    }

    @Override
    public void accept(final MetadataRepository source) {
        target = new FilteredMetadataRepository(source, entities);
        consumer.accept(target);
    }
    
    public List<String> getIncludedEntities() {
        return target.getEntities().stream()
                .filter(ent -> !entities.contains(ent.getFullName()))
                .filter(ent -> !ent.isAbstractClass())
                .map(Entity::getFullName)
                .collect(toList());
    }
}
