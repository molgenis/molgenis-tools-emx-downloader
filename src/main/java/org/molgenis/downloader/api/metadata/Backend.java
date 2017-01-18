/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david
 */
public enum Backend {
    POSTGRESQL("PostgreSQL"), MYSQL("MySQL"), EL("ElasticSearch"), ID_CARD("ID-Card");
    
    private static final Map<String, Backend> naming;
    
    static {
        naming = new HashMap<>();
        naming.put(POSTGRESQL.backend, POSTGRESQL);
        naming.put(MYSQL.backend, MYSQL);
        naming.put(EL.backend, EL);
        naming.put(ID_CARD.backend, ID_CARD);
    }
    
    private final String backend;
    
    Backend(final String name) {
        backend = name;
    }

    public static Backend from(final String name) {
        return naming.get(name);
    }
    
    public String getBackend() {
        return backend;
    }
}
