
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.MetadataRepository;

import java.util.*;
import java.util.stream.Collectors;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;


class FilteredMetadataRepository implements MetadataRepository {

    private final Set<Entity> entities;
    private final Set<Attribute> attributes;
    private final Set<Package> packages;
    private final Set<Tag> tags;
    private final Collection<Language> languages;
    

    public FilteredMetadataRepository(final MetadataRepository source, final List<String> entities) {
        this.entities = new LinkedHashSet<>();
        attributes = new LinkedHashSet<>();
        packages = new LinkedHashSet<>();
        tags = new LinkedHashSet<>();
        languages = source.getLanguages();
        source.getEntities().stream()
                .filter((ent) -> entities.contains(ent.getFullName())).forEach(this::traverse);
    }

    private void traverse(final Entity entity) {
        if (entity != null && !entities.contains(entity)) {
            entities.add(entity);
            tags.addAll(entity.getTags());
            traverse(entity.getPkg());
            traverse(entity.getBase());
            entity.getAttributes().forEach(this::traverse);
        }
    }

    private void traverse(final Package pkg) {
        if (pkg != null && !packages.contains(pkg)) {
            packages.add(pkg);
            tags.addAll(pkg.getTags());
            traverse(pkg.getParent());
        }
    }

    private void traverse(final Attribute att) {
        if (att != null && !attributes.contains(att)) {
            attributes.add(att);
            tags.addAll(att.getTags());
            att.getParts().forEach(this::traverse);
            traverse(att.getRefEntity());
        }
    }

    @Override
    public final Collection<Attribute> getAttributes() {
        return attributes.stream().sorted(Comparator.comparing(x -> x.getEntityFullname())).collect(Collectors.toList());
    }

    @Override
    public final Collection<Entity> getEntities() {
        return entities.stream().sorted((x, y) -> {
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
        return packages.stream().sorted(Comparator.comparing(Package::getName)).collect(Collectors.toList());
    }

    @Override
    public final Collection<Tag> getTags() {
        return tags.stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Language> getLanguages() {
        return languages;
    }    
}
