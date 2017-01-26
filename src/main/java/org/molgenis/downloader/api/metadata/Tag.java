
package org.molgenis.downloader.api.metadata;

import java.net.URI;
import java.util.Objects;


public class Tag implements Metadata {
    
    private String id;
    private String label;
    private URI objectIRI;
    private URI relationIRI;
    private String relationLabel;
    private String codeSystem;

    public URI getObjectIRI() {
        return objectIRI;
    }

    public URI getRelationIRI() {
        return relationIRI;
    }

    public String getRelationLabel() {
        return relationLabel;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tag tag = (Tag) o;

		if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
		if (label != null ? !label.equals(tag.label) : tag.label != null) return false;
		if (objectIRI != null ? !objectIRI.equals(tag.objectIRI) : tag.objectIRI != null) return false;
		if (relationIRI != null ? !relationIRI.equals(tag.relationIRI) : tag.relationIRI != null) return false;
		return (relationLabel != null ? relationLabel.equals(tag.relationLabel) : tag.relationLabel == null) && (
				codeSystem != null ? codeSystem.equals(tag.codeSystem) : tag.codeSystem == null);
	}

    public Tag(final String id) {
        this.id = id;
    }
    
    public static Tag from(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new Tag(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setObjectIRI(URI objectIRI) {
        this.objectIRI = objectIRI;
    }

    public void setRelationIRI(URI relationIRI) {
        this.relationIRI = relationIRI;
    }

    public void setRelationLabel(String relationLabel) {
        this.relationLabel = relationLabel;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    @Override
    public String toString()
    {
        return "Tag{" + "id='" + id + '\'' + ", label='" + label + '\'' + ", objectIRI=" + objectIRI + ", relationIRI="
                + relationIRI + ", relationLabel='" + relationLabel + '\'' + ", codeSystem='" + codeSystem + '\'' + '}';
    }
}
