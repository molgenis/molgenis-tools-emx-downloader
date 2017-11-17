package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import java.util.Map;

interface MetadataConverter
{

	Attribute toAttribute(final Map<String, String> data);

	Entity toEntity(final Map<String, String> data);

	Package toPackage(final Map<String, String> data);

	Tag toTag(final Map<String, String> data);

	Language toLanguage(final Map<String, String> data);

	String getTagsRepositoryName();

	String getPackagesRepositoryName();

	String getEntitiesRepositoryName();

	String getAttributesRepositoryName();

	String getLanguagesRepositoryName();

	default void postProcess(WriteableMetadataRepository repository)
	{
	}

	;
}
