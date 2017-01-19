/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.util.function.Consumer;

/**
 *
 * @author david
 */
public interface MetadataConsumer extends AutoCloseable, Consumer<MetadataRepository>  {

    @Override
    default void close() throws Exception {

    }

}
