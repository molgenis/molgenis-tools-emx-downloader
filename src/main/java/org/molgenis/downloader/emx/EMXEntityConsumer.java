/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.EMXWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.EMXDataStore;

public class EMXEntityConsumer implements EntityConsumer {

    private final List<Attribute> attributes;
    private final EMXDataStore sheet;
    private final EMXWriter writer;

    public EMXEntityConsumer(final EMXWriter writer, final Entity entity) throws IOException {
        this.writer = writer;
        attributes = setAttributes(entity);
        final List<String> values = getAttributes().stream().map(att -> att.getName()).collect(Collectors.toList());

        sheet = writer.createDataStore(entity.getFullName());
        sheet.writeRow(values);
    }

    @Override
    public void accept(Map<Attribute, String> data) {
        final List<String> values = new ArrayList<>();
        for (int index = 0; index < getAttributes().size(); index++) {
            final String value = data.get(getAttributes().get(index));
            if (value != null && !value.trim().isEmpty()) {
                values.add(value.trim());
            } else {
                values.add(null);
            }
        }
        try {
            sheet.writeRow(values);
        } catch (final IOException ex) {
            writer.logError(ex);
        }
    }
    
    public final List<Attribute> getAttributes() {
        return attributes;
    }

    private List<Attribute> getParts(final Attribute compound) {
        List<Attribute> atts = new ArrayList<>();
        compound.getParts().forEach((Attribute att) -> {
            if (att.getDataType().equals(DataType.COMPOUND)) {
                atts.addAll(getParts(att));
            } else {
                atts.add(att);
            }
        });
        return atts;
    }
    
    private List<Attribute> setAttributes(final Entity entity) {
        List<Attribute> atts = new ArrayList<>();
        entity.getAttributes().forEach((Attribute att) -> {
            if (att.getDataType().equals(DataType.COMPOUND)) {
                atts.addAll(getParts(att));
            } else {
                atts.add(att);
            }
        });
        return atts;
    }
}
