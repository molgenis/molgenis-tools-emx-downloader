
package org.molgenis.downloader.api;

import java.io.IOException;
import java.util.List;
import org.molgenis.downloader.api.metadata.Entity;


public interface EMXWriter {
    
    EntityConsumer createConsumerForEntity(final Entity entity) throws IOException;
    
    MetadataConsumer createMetadataConsumer();
    
    EMXDataStore createDataStore(final String name) throws IOException;
    
    boolean hasExceptions();
    
    List<Exception> getExceptions();
    
    void addException(final Exception ex);
}
