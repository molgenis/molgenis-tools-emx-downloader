package org.molgenis.downloader.rdf;

import com.google.common.io.Files;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.molgenis.downloader.util.ConsoleWriter;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteQuietly;

public class RdfBackend implements AutoCloseable
{
	private static final String INDEXES = "spoc,posc,cosp";
	private final Repository repository;
	private final File storeDir;

	public RdfBackend()
	{
		storeDir = Files.createTempDir();
		repository = new SailRepository(new NativeStore(storeDir, INDEXES));
		repository.initialize();
	}

	public Repository getRepository()
	{
		return repository;
	}

	@Override
	public void close() throws Exception
	{
		try
		{
			repository.shutDown();
		}
		catch (Exception ex)
		{
			ConsoleWriter.writeToConsole("Failed to shut down repository.", ex);
			throw ex;
		}
		finally
		{
			if (!deleteQuietly(storeDir))
			{
				ConsoleWriter.writeToConsole("Failed to delete temporary repository directory.");
			}
		}

	}
}