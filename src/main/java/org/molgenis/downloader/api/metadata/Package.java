
package org.molgenis.downloader.api.metadata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Package implements Metadata {
    private String name;
    private String description;
    private Package parent;
    private final Set<Tag> tags;
    private String label;

    private Package(final String name) {
        tags = new HashSet<>();        
        this.name = name;
    }

    public static Package from(final String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new Package(fullName);
    }
    
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.parent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Package other = (Package) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.parent, other.parent);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public String getDescription() {
        return description;
    }

    public Package getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParent(Package parent) {
        this.parent = parent;
    }
    
    public void addTag(final Tag tag) {
        tags.add(tag);
    }
}
