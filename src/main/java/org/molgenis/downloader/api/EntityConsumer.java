package org.molgenis.downloader.api;

import java.util.Map;
import java.util.function.Consumer;

public interface EntityConsumer extends AutoCloseable, Consumer<Map<String, String>>
{

	@Override
	default void close() throws Exception
	{

	}
}
