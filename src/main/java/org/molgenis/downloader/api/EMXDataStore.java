/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author david
 */
public interface EMXDataStore {
    void writeRow(final List<String> values) throws IOException;    
}
