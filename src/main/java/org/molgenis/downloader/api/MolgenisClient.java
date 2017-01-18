/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.io.IOException;
import java.net.URISyntaxException;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;

/**
 *
 * @author david
 */
public interface MolgenisClient extends AutoCloseable {

    boolean login(final String username, final String password) throws Exception;

    boolean logout() throws Exception;
    
    void streamEntityData(final String name, final EntityConsumer consumer);
    
    Entity getEntity(final String name) throws IOException, URISyntaxException;
    
    void getMetaData(final MetadataConsumer consumer);

    MolgenisVersion version() throws IOException, URISyntaxException;
}
