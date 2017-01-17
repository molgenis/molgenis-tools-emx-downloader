/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.util.Collection;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;

/**
 *
 * @author david
 */
public interface MetadataRepository {

    Collection<Attribute> getAttributes();

    Collection<Entity> getEntities();

    Collection<Language> getLanguages();

    Collection<Package> getPackages();

    Collection<Tag> getTags();
    
}
