package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.EMXBackend;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.emx.excel.ExcelBackend;
import org.molgenis.downloader.emx.tsv.ZipFileBackend;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EMXClient implements AutoCloseable
{

	private static final String XLSX = ".xlsx";
	private static final String XLS = ".xls";
	private final MolgenisClient molgenisClient;
	private final List<Exception> exceptions;

	public EMXClient(final MolgenisClient client)
	{
		this.molgenisClient = client;
		this.exceptions = new ArrayList<>();
	}

	public boolean downloadEMX(final List<String> entities, final Path path, final boolean includeMetadata,
			boolean overwrite, MolgenisVersion version, Integer pageSize) throws Exception
	{
		try (final EMXBackend backend = createBackend(path, overwrite))
		{
			final EMXFileWriter writer = new EMXFileWriter(backend, version);
			List<String> target = new ArrayList<>(entities);
			if (includeMetadata)
			{
				try (final MetadataConsumer consumer = writer.createMetadataConsumer())
				{
					final MetadataFilter filter = new MetadataFilter(entities, consumer, version);
					molgenisClient.streamMetadata(filter, version);
					target.addAll(filter.getIncludedEntities());
					target = target.stream().distinct().collect(Collectors.toList());
				}
			}
			for (final String name : target)
			{
				try (final EntityConsumer consumer = writer.createConsumerForEntity(molgenisClient.getEntity(name)))
				{
					molgenisClient.streamEntityData(name, consumer, pageSize);
				}
				catch (final org.json.JSONException ex)
				{
					writer.addException(new IllegalArgumentException("entity: " + name + " does not exist", ex));
				}
			}
			exceptions.addAll(writer.getExceptions());
			return writer.hasExceptions();
		}
	}

	public List<Exception> getExceptions()
	{
		return exceptions;
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
				throw new FileAlreadyExistsException(
						String.format("File %s already exists, please use the '-o' option to overwrite.", path));
			}
		}
		return backend;
	}

	@Override
	public void close() throws Exception
	{
	}
}
