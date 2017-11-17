package org.molgenis.downloader.api;

import java.io.IOException;

public interface EMXBackend extends AutoCloseable
{

	EMXDataStore createDataStore(final String name) throws IOException;

}
