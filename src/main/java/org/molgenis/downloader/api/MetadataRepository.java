package org.molgenis.downloader.api;

import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.api.metadata.Package;

import java.util.Collection;

public interface MetadataRepository
{

	Collection<Attribute> getAttributes();

	Collection<Entity> getEntities();

	Collection<Language> getLanguages();

	Collection<Package> getPackages();

	Collection<Tag> getTags();

	default Entity getEntity(String fullName)
	{
		return getEntities().stream()
							.filter(candidate -> candidate.getFullName().equals(fullName))
							.findFirst()
							.orElseThrow(() -> new IllegalArgumentException(
									"Entity with fullName " + fullName + " not found"));
	}

}
