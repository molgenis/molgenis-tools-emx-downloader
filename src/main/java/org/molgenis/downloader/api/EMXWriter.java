/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.io.IOException;
import java.util.List;
import org.molgenis.downloader.api.metadata.Entity;

/**
 *
 * @author david
 */
public interface EMXWriter {
    
    EntityConsumer createConsumerForEntity(final Entity entity) throws IOException;
    
    MetadataConsumer createMetadataConsumer();
    
    EMXDataStore createDataStore(final String name) throws IOException;
    
    boolean hasExceptions();
    
    List<Exception> getExceptions();
    
    void addException(final Exception ex);
}
