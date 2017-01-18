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
    
    public EntityConsumer createConsumerForEntity(final Entity entity) throws IOException;
    
    public MetadataConsumer createMetadataConsumer() throws IOException;
    
    EMXDataStore createDataStore(final String name) throws IOException;
    
    boolean hasErrors();
    
    List<Exception> getErrors();
    
    void logError(final Exception ex);
}
