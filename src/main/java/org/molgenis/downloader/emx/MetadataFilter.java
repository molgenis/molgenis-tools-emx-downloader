/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.MetadataRepository;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.molgenis.downloader.api.MetadataConsumer;

/**
 *
 * @author david
 */
public class MetadataFilter implements MetadataConsumer {

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
                .map(ent -> ent.getFullName())
                .collect(toList());
    }
}
