
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.EMXBackend;
import java.io.IOException;
import java.net.URISyntaxException;
import org.molgenis.downloader.emx.excel.ExcelBackend;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.emx.tsv.ZipFileBackend;


public class EMXClient implements AutoCloseable {

    private static final String XLSX = ".xlsx";
    private static final String XLS = ".xls";
    private final MolgenisClient molgenis;
    private final List<Exception> errors;

    public EMXClient(final MolgenisClient client) {
        molgenis = client;
        errors = new ArrayList<>();
    }

    public boolean downloadEMX(final List<String> entities, final Path path, final boolean includeMetadata,
            boolean overwrite) throws Exception {
        try (final EMXBackend backend = createBackend(path, overwrite)) {
            final EMXFileWriter writer = new EMXFileWriter(backend, molgenis.version());
            final List<String> target = new ArrayList<>(entities);
            if (includeMetadata) {
                try (final MetadataConsumer consumer = writer.createMetadataConsumer()) {
                    final MetadataFilter filter = new MetadataFilter(entities, consumer);
                    molgenis.getMetaData(filter);
                    target.addAll(filter.getIncludedEntities());
                }
            }
            for (final String name : target) {
                try (final EntityConsumer consumer = writer.createConsumerForEntity(molgenis.getEntity(name))) {
                    molgenis.streamEntityData(name, consumer);
                }
            }
            errors.addAll(writer.getExceptions());
            return writer.hasExceptions();
        }
    }

    public List<Exception> getErrors() {
        return errors;
    }

    private EMXBackend createBackend(final Path location, boolean overwrite) throws IOException, URISyntaxException {
        final EMXBackend backend;
        if (location.toString().endsWith(XLSX) || location.toString().endsWith(XLS)) {
            backend = new ExcelBackend(location, overwrite);
        } else {
            backend = new ZipFileBackend(location, overwrite);
        }
        return backend;
    }

    @Override
    public void close() throws Exception {
    }
}
