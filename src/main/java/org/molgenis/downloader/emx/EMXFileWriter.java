/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.EMXWriter;
import org.molgenis.downloader.api.EMXBackend;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.api.EMXDataStore;

/**
 *
 * @author david
 */
public class EMXFileWriter implements EMXWriter {

    private final MolgenisVersion version;
    private final List<Exception> errors;
    private final EMXBackend backend;

    public EMXFileWriter(final EMXBackend store, final MolgenisVersion molgenisVersion) throws IOException, URISyntaxException {
        version = molgenisVersion;
        errors = new ArrayList<>();
        backend = store;
    }

    @Override
    public EntityConsumer createConsumerForEntity(final Entity entity) throws IOException {
        return new EMXEntityConsumer(this, entity);
    }

    @Override
    public MetadataConsumer createMetadataConsumer() throws IOException {
        return new EMXMetadataConsumer(this, version);
    }

    @Override
    public EMXDataStore createDataStore(String name) throws IOException {
        return backend.createDataStore(name);
    }

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public List<Exception> getErrors() {
        return errors;
    }

    @Override
    public void logError(final Exception ex) {
        errors.add(ex);
    }
}
