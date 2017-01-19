
package org.molgenis.downloader.api;

import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;


public interface WriteableMetadataRepository extends MetadataRepository {

    Attribute createAttribute(final String identifier);

    Entity createEntity(final String fullName);

    Entity createEntity(final String name, final String pkgName);

    Language createLanguage(final String code);

    Package createPackage(final String fullName);

    Tag createTag(final String identifier);
    
}
