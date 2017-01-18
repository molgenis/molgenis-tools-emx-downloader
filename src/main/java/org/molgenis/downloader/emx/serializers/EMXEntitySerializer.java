/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx.serializers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import static java.util.stream.Collectors.joining;

/**
 *
 * @author david
 */
public class EMXEntitySerializer implements EntitySerializer<Entity> {
    private static final String[] FIELDS = { "name", "package", "extends",
         "abstract", "backend", "tags", "label", "description" };
    
    private final Collection<Language> languages;

    public EMXEntitySerializer(final Collection<Language> languages) {
        this.languages = languages;
    }
    
    @Override
    public List<String> serialize(final Entity entity) {
        List<String> result = new ArrayList<>();
        result.add(entity.getShortName());
        result.add(Optional.ofNullable(entity.getPkg()).map(Package::getName).orElse(""));
        result.add(Optional.ofNullable(entity.getBase()).map(Entity::getFullName).orElse(""));
        result.add(Boolean.toString(entity.isAbstractClass()));
        // MYSQL and POSTGRESQL backends are the defaults for MOLGENIS 1 and 2 respectively
        // should not be listed.
        result.add(Optional.ofNullable(entity.getBackend()).filter(backend -> !(backend.equals(Backend.MYSQL) || backend.equals(Backend.POSTGRESQL))).map(Backend::getBackend).orElse(""));
        result.add(entity.getTags().stream().map(Tag::getId).collect(joining(",")));
        result.add(entity.getLabel());
        result.add(entity.getDescription());
        languages.forEach(language -> {
            result.add(entity.getDescriptions().get(language));
            result.add(entity.getLabels().get(language));
        });        
        return result;
    }

    @Override
    public List<String> fields() {
        final List<String> fields = new ArrayList<>(Arrays.asList(FIELDS));
        languages.forEach(language -> {
            fields.add("description-" + language.getCode());
            fields.add("label-" + language.getCode());
        });
        return fields;
    }
     
}
