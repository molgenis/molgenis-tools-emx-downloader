package org.molgenis.downloader.api;

import java.util.function.Consumer;

public interface MetadataConsumer extends AutoCloseable, Consumer<MetadataRepository>
{

	@Override
	default void close() throws Exception
	{

	}

}
