package org.molgenis.downloader.emx.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.molgenis.downloader.api.EMXDataStore;

import java.io.IOException;
import java.util.List;

public class ExcelSheet implements EMXDataStore
{

	private final Sheet sheet;
	private int rowNumber;

	public ExcelSheet(final Workbook workbook, final String name)
	{
		sheet = workbook.createSheet(name);
		rowNumber = 0;
	}

	@Override
	public void writeRow(List<String> values) throws IOException
	{
		final Row row = sheet.createRow(rowNumber);
		rowNumber++;
		for (int index = 0; index < values.size(); index++)
		{
			final String record = values.get(index);
			if (record != null && !record.trim().isEmpty())
			{
				final Cell cell = row.createCell(index);
				cell.setCellValue(record.trim());
			}
		}
	}
}
