/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;

/**
 *
 * @author david
 */
public interface WriteableMetadataRepository extends MetadataRepository {

    Attribute createAttribute(final String identifier);

    Entity createEntity(final String fullName);

    Entity createEntity(final String name, final String pkgName);

    Language createLanguage(final String code);

    Package createPkg(final String fullName);

    Tag createTag(final String identifier);
    
}
