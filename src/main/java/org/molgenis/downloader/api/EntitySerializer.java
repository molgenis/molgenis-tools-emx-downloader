package org.molgenis.downloader.api;

import org.molgenis.downloader.api.metadata.Metadata;

import java.util.List;

public interface EntitySerializer<T extends Metadata>
{
	List<String> serialize(T entity);

	List<String> fields();
}
