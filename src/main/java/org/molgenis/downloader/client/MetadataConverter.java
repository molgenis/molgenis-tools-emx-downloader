
package org.molgenis.downloader.client;

import java.util.Map;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;


interface MetadataConverter {

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
}
