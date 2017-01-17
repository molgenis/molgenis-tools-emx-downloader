/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api.metadata;

import java.net.URI;
import java.util.Objects;

/**
 *
 * @author david
 */
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
        final Tag other = (Tag) obj;
        return Objects.equals(this.id, other.id);
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
}
