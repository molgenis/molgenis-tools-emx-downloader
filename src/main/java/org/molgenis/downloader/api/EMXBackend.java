/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.io.IOException;

/**
 *
 * @author david
 */
public interface EMXBackend extends AutoCloseable {
    
    EMXDataStore createDataStore(final String name) throws IOException;
    
    
}
