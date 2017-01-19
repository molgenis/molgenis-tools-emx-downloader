/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.MetadataRepository;

import java.util.*;
import java.util.stream.Collectors;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;

/**
 *
 * @author david
 */
class FilteredMetadataRepository implements MetadataRepository {

    private final Set<Entity> ents;
    private final Set<Attribute> atts;
    private final Set<Package> pkgs;
    private final Set<Tag> tags;
    private final Collection<Language> lngs;
    

    public FilteredMetadataRepository(final MetadataRepository source, final List<String> entities) {
        ents = new LinkedHashSet<>();
        atts = new LinkedHashSet<>();
        pkgs = new LinkedHashSet<>();
        tags = new LinkedHashSet<>();
        lngs = source.getLanguages();
        source.getEntities().stream()
                .filter((ent) -> entities.contains(ent.getFullName())).forEach(this::traverse);
    }

    private void traverse(final Entity entity) {
        if (entity != null && !ents.contains(entity)) {
            ents.add(entity);
            tags.addAll(entity.getTags());
            traverse(entity.getPkg());
            traverse(entity.getBase());
            entity.getAttributes().forEach(this::traverse);
        }
    }

    private void traverse(final Package pkg) {
        if (pkg != null && !pkgs.contains(pkg)) {
            pkgs.add(pkg);
            tags.addAll(pkg.getTags());
            traverse(pkg.getParent());
        }
    }

    private void traverse(final Attribute att) {
        if (att != null && !atts.contains(att)) {
            atts.add(att);
            tags.addAll(att.getTags());
            att.getParts().forEach(this::traverse);
            traverse(att.getEntity());
            traverse(att.getRefEntity());
        }
    }

    @Override
    public final Collection<Attribute> getAttributes() {
        return atts.stream().sorted(Comparator.comparing(x -> x.getEntity().getFullName())).collect(Collectors.toList());
    }

    @Override
    public final Collection<Entity> getEntities() {
        return ents.stream().sorted((x, y) -> {
            if (x.isParentOf(y)) {
                return -1;
            }
            if (y.isParentOf(x)) {
                return 1;
            }
            if (x.isAbstractClass() != y.isAbstractClass()) {
                return x.isAbstractClass() ? -1 : 1;                
            }
            return x.getFullName().compareTo(y.getFullName());
        }).collect(Collectors.toList());
    }

    @Override
    public final Collection<Package> getPackages() {
        return pkgs.stream().sorted(Comparator.comparing(Package::getName)).collect(Collectors.toList());
    }

    @Override
    public final Collection<Tag> getTags() {
        return tags.stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Language> getLanguages() {
        return lngs;
    }    
}
