
package org.molgenis.downloader.api;

import java.io.IOException;
import java.net.URISyntaxException;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;


public interface MolgenisClient extends AutoCloseable {

    void login(final String username, final String password);

    boolean logout();
    
    default void streamEntityData(final String name, final EntityConsumer consumer){
        streamEntityData(name, consumer, null);
    }

    void streamEntityData(final String name, final EntityConsumer consumer, Integer pageSize);
    
    Entity getEntity(final String name) throws IOException, URISyntaxException;
    
    void streamMetadata(final MetadataConsumer consumer);

    MolgenisVersion getVersion() throws IOException, URISyntaxException;
}
