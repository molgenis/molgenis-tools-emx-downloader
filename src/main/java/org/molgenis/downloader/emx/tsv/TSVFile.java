/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx.tsv;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.molgenis.downloader.api.EMXDataStore;

/**
 *
 * @author david
 */
public class TSVFile implements AutoCloseable, EMXDataStore {

    public static final String TSV = ".tsv";
    private final CSVPrinter printer;

    public TSVFile(final FileSystem fs, final String name) throws IOException {
        printer = new CSVPrinter(Files.newBufferedWriter(fs.getPath("/" + name + TSV)), CSVFormat.TDF);
    }

    @Override
    public void close() throws Exception {
        printer.close();
    }

    @Override
    public void writeRow(final List<String> values) throws IOException {
        printer.printRecord(values);
    }
}
