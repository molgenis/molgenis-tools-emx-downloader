
package org.molgenis.downloader.api.metadata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class Entity implements Metadata {

    private String fullName;
    private final Set<Attribute> attributes;
    private Entity base;
    private Package pkg;
    private boolean abstractClass;
    private String description;
    private final Map<Language, String> descriptions;
    private String label;
    private final Map<Language, String> labels;
    private final Set<Tag> tags;
    private Backend backend;
    private Attribute idAttribute;
    private Attribute labelAttribute;
    private Set<Attribute> lookupAttributes;
    private boolean rowLevelSecured;

    public boolean isRowLevelSecured() {
        return rowLevelSecured;
    }

    public Entity() {
        attributes = new LinkedHashSet<>();
        descriptions = new HashMap<>();
        labels = new HashMap<>();
        tags = new HashSet<>();
        lookupAttributes = new HashSet<>();
    }
    
    public Entity(final String fullName) {
        this();
        this.fullName = fullName;
    }

    public Entity(final String shortName, final String pkg) {
        this();
        fullName = pkg + "_" + shortName;
    }
    
    public static Entity from(final String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new Entity(fullName);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.fullName);
        hash = 59 * hash + Objects.hashCode(this.pkg);
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
        final Entity other = (Entity) obj;
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        return Objects.equals(this.pkg, other.pkg);
    }

    public boolean isParentOf(final Entity entity) {
        if (entity == null || entity.getBase() == null) {
            return false;
        }
        if (this.equals(entity.getBase())) {
            return true;
        }
        return this.isParentOf(entity.getBase());
    }

    public Attribute getLabelAttribute() {
        return labelAttribute;
    }

    public Set<Attribute> getLookupAttributes() {
        return lookupAttributes;
    }

    public Attribute getIdAttribute() {
        return idAttribute;
    }

    public Entity getBase() {
        return base;
    }

    public Package getPkg() {
        return pkg;
    }

    public boolean isAbstractClass() {
        return abstractClass;
    }

    public String getDescription() {
        return description;
    }

    public Map<Language, String> getDescriptions() {
        return descriptions;
    }

    public String getLabel() {
        return label;
    }

    public Map<Language, String> getLabels() {
        return labels;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Backend getBackend() {
        return backend;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getShortName() {
        String prefix = pkg == null ? "" : pkg.getName() + "_";
        return fullName.replaceFirst(prefix, "");
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBase(Entity base) {
        this.base = base;
    }

    public void setPackage(Package pkg) {
        this.pkg = pkg;
    }

    public void setAbstractClass(boolean abstractClass) {
        this.abstractClass = abstractClass;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }

    public void setIdAttribute(Attribute idAttribute) {
        this.idAttribute = idAttribute;
    }

    public void setLabelAttribute(Attribute labelAttribute) {
        this.labelAttribute = labelAttribute;
    }

    public void setLookupAttributes(Set<Attribute> lookupAttributes) {
        this.lookupAttributes = lookupAttributes;
    }

    public void setRowLevelSecured(boolean rowLevelSecured) {
        this.rowLevelSecured = rowLevelSecured;
    }

    public void addAttribute(final Attribute att) {
        attributes.add(att);
    }
    
    public void addLookupAttribute(final Attribute att) {
        attributes.add(att);
    }
    
    public void addTag(final Tag tag) {
        tags.add(tag);
    }

    public void addDescription(final String description, final Language language) {
        descriptions.put(language, description);
    }
    
    public void addLabel(final String label, final Language language) {
        labels.put(language, label);
    }

    @Override
    public String toString()
    {
        return "Entity{" + "fullName='" + fullName + '\'' + ", attributes=" + attributes + ", base=" + base + ", pkg="
                + pkg + ", abstractClass=" + abstractClass + ", description='" + description + '\'' + ", descriptions="
                + descriptions + ", label='" + label + '\'' + ", labels=" + labels + ", tags=" + tags + ", backend="
                + backend + ", idAttribute=" + idAttribute + ", labelAttribute=" + labelAttribute
                + ", lookupAttributes=" + lookupAttributes + ", rowLevelSecured=" + rowLevelSecured + '}';
    }
}
