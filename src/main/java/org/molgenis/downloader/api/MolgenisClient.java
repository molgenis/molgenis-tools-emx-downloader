
package org.molgenis.downloader.api;

import java.io.IOException;
import java.net.URISyntaxException;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;


public interface MolgenisClient extends AutoCloseable {

    void login(final String username, final String password);

    boolean logout();
    
    void streamEntityData(final String name, final EntityConsumer consumer);
    
    Entity getEntity(final String name) throws IOException, URISyntaxException;
    
    void getMetaData(final MetadataConsumer consumer);

    MolgenisVersion version() throws IOException, URISyntaxException;
}
