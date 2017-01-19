/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.util.List;
import org.molgenis.downloader.api.metadata.Metadata;

/**
 *
 * @author david
 */
public interface EntitySerializer<T extends Metadata> {
    List<String> serialize(T entity);
    List<String> fields();
}
