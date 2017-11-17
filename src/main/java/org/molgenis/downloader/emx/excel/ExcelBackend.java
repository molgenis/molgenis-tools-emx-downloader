package org.molgenis.downloader.emx.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.molgenis.downloader.api.EMXBackend;
import org.molgenis.downloader.api.EMXDataStore;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExcelBackend implements EMXBackend
{

	private final Workbook workbook;
	private final Path path;

	public ExcelBackend(final Path path, boolean overwrite) throws FileAlreadyExistsException
	{
		if (path.toFile().exists() && !overwrite)
		{
			throw new FileAlreadyExistsException(path.toString(), null, "File already exists.");
		}
		workbook = new XSSFWorkbook();
		this.path = path;
	}

	@Override
	public void close() throws Exception
	{
		workbook.write(Files.newOutputStream(path));
		workbook.close();
	}

	@Override
	public EMXDataStore createDataStore(String name)
	{
		return new ExcelSheet(workbook, name);
	}
}
