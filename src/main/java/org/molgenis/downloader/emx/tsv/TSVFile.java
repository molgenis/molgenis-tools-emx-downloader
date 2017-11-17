package org.molgenis.downloader.emx.tsv;

import au.com.bytecode.opencsv.CSVWriter;
import org.molgenis.downloader.api.EMXDataStore;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.List;

/**
 * @author david
 */
public class TSVFile implements AutoCloseable, EMXDataStore
{

	private static final String TSV = ".tsv";
	private static final char TAB = '\t';

	private final CSVWriter csvWriter;

	public TSVFile(final FileSystem fs, final String name) throws IOException
	{
		csvWriter = new CSVWriter(Files.newBufferedWriter(fs.getPath("/" + name + TSV)), TAB);
	}

	@Override
	public void close() throws Exception
	{
		csvWriter.close();
	}

	@Override
	public void writeRow(final List<String> values) throws IOException
	{
		csvWriter.writeNext(values.toArray(new String[values.size()]));
	}
}
