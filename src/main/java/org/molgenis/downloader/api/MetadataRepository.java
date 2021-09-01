package org.molgenis.downloader.api;

import java.util.Collection;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;

public interface MetadataRepository {

  Collection<Attribute> getAttributes();

  Collection<Entity> getEntities();

  Collection<Language> getLanguages();

  Collection<Package> getPackages();

  Collection<Tag> getTags();

  default Entity getEntity(String fullName) {
    return getEntities().stream()
        .filter(candidate -> candidate.getFullName().equals(fullName))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("Entity with fullName " + fullName + " not found"));
  }
}
