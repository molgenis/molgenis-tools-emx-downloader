package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.EMXBackend;

import java.io.IOException;
import java.net.URISyntaxException;

import org.molgenis.downloader.emx.excel.ExcelBackend;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.emx.tsv.ZipFileBackend;

import static org.molgenis.downloader.util.ConsoleWriter.writeToConsole;

public class EMXClient implements AutoCloseable
{

	private static final String XLSX = ".xlsx";
	private static final String XLS = ".xls";
	private final MolgenisClient molgenis;
	private final List<Exception> errors;

	public EMXClient(final MolgenisClient client)
	{
		molgenis = client;
		errors = new ArrayList<>();
	}

	public boolean downloadEMX(final List<String> entities, final Path path, final boolean includeMetadata,
			boolean overwrite) throws Exception
	{
		try (final EMXBackend backend = createBackend(path, overwrite))
		{
			final EMXFileWriter writer = new EMXFileWriter(backend, molgenis.version());
			final List<String> target = new ArrayList<>(entities);
			if (includeMetadata)
			{
				try (final MetadataConsumer consumer = writer.createMetadataConsumer())
				{
					final MetadataFilter filter = new MetadataFilter(entities, consumer);
					molgenis.getMetaData(filter);
					target.addAll(filter.getIncludedEntities());
				}
			}
			for (final String name : target)
			{
				try (final EntityConsumer consumer = writer.createConsumerForEntity(molgenis.getEntity(name)))
				{
					molgenis.streamEntityData(name, consumer);
				}
			}
			errors.addAll(writer.getExceptions());
			return writer.hasExceptions();
		}
	}

	public List<Exception> getErrors()
	{
		return errors;
	}

	private EMXBackend createBackend(final Path path, boolean overwrite) throws IOException, URISyntaxException
	{
		final EMXBackend backend;
		if (path.toString().endsWith(XLSX) || path.toString().endsWith(XLS))
		{
			backend = new ExcelBackend(path, overwrite);
		}
		else
		{
			boolean fileExists = Files.exists(path);
			if (!fileExists || overwrite)
			{
				if (fileExists)
				{
					Files.delete(path);
				}
				backend = new ZipFileBackend(path);
			}
			else
			{
				throw new FileAlreadyExistsException(String.format("File %s already exists, please use the '-o' option to overwrite.", path));
			}
		}
		return backend;
	}

	@Override
	public void close() throws Exception
	{
	}
}
