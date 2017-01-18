/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import java.util.Collection;
import java.util.LinkedHashMap;
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
class MetadataRepository implements WriteableMetadataRepository {

    private final Map<String, Tag> tags;
    private final Map<String, Package> packages;
    private final Map<String, Entity> entities;
    private final Map<String, Attribute> attributes;
    private final Map<String, Language> languages;

    public MetadataRepository() {
        tags = new LinkedHashMap<>();
        packages = new LinkedHashMap<>();
        entities = new LinkedHashMap<>();
        attributes = new LinkedHashMap<>();
        languages = new LinkedHashMap<>();
    }

    @Override
    public Collection<Tag> getTags() {
        return tags.values();
    }

    @Override
    public Collection<Package> getPackages() {
        return packages.values();
    }

    @Override
    public Collection<Entity> getEntities() {
        return entities.values();
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return attributes.values();
    }

    @Override
    public Collection<Language> getLanguages() {
        return languages.values();
    }

    @Override
    public Language createLanguage(final String code) {
        return languages.computeIfAbsent(code, Language::from);
    }
    @Override
    public Tag createTag(final String identifier) {
        return tags.computeIfAbsent(identifier, Tag::from);
    }

    @Override
    public Package createPkg(final String fullName) {
        return packages.computeIfAbsent(fullName, Package::from);
    }
    
    @Override
    public Entity createEntity(final String fullName) {
        return entities.computeIfAbsent(fullName, Entity::from);
    }
    
    @Override
    public Entity createEntity(final String name, final String pkgName) {
        final String fullName = pkgName + "_" + name;
        return entities.computeIfAbsent(fullName, Entity::from);
    }
    
    @Override
    public Attribute createAttribute(final String identifier) {
        return attributes.computeIfAbsent(identifier, Attribute::from);
    }
}
