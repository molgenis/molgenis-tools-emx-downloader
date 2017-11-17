package org.molgenis.downloader.api;

import java.io.IOException;
import java.util.List;

public interface EMXDataStore
{
	void writeRow(final List<String> values) throws IOException;
}
