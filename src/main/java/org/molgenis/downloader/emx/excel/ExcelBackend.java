/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.molgenis.downloader.emx.excel;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.molgenis.downloader.api.EMXBackend;
import org.molgenis.downloader.api.EMXDataStore;

/**
 *
 * @author david
 */
public class ExcelBackend implements EMXBackend {

    private final Workbook workbook;
    private final Path path;

    public ExcelBackend(final Path path) throws FileAlreadyExistsException {
        if (path.toFile().exists()) {
            throw new FileAlreadyExistsException(path.toString(), null,
                    "File already exists.");
        }
        workbook = new XSSFWorkbook();
        this.path = path;
    }


    @Override
    public void close() throws Exception {
        workbook.write(Files.newOutputStream(path));
        workbook.close();
    }

    @Override
    public EMXDataStore createDataStore(String name) {
        return new ExcelSheet(workbook, name);
    }
}
