package org.molgenis.downloader.api;

import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

public interface WriteableMetadataRepository extends MetadataRepository
{

	Attribute createAttribute(final String identifier);

	Entity createEntity(final String fullName);

	Entity createEntityById(final String id, final String name);

	Entity createEntity(final String name, final String pkgName);

	Language createLanguage(final String code);

	Package createPackage(final String fullName);

	Package createPackageById(String id);

	Tag createTag(final String identifier);

}
