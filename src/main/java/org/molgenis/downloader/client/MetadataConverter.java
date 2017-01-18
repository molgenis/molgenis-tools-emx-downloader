/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.client;

import java.util.Map;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;

/**
 *
 * @author david
 */
interface MetadataConverter {

    Attribute toAttribute(final Map<Attribute, String> data);

    Entity toEntity(final Map<Attribute, String> data);

    Package toPackage(final Map<Attribute, String> data);

    Tag toTag(final Map<Attribute, String> data);
    
    Language toLanguage(final Map<Attribute, String> data);
    
    String getTagsRepository();
    String getPackagesRepository();
    String getEnitiesRepository();
    String getAttributesRepository();
    String getLanguagesRepository();
}
