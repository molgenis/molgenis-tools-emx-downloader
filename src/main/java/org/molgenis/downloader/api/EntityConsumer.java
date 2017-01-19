/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.api;

import java.util.Map;
import java.util.function.Consumer;
import org.molgenis.downloader.api.metadata.Attribute;

/**
 *
 * @author david
 */
public interface EntityConsumer extends AutoCloseable, Consumer<Map<Attribute, String>> {

    @Override
	default void close() throws Exception {

    }
}
