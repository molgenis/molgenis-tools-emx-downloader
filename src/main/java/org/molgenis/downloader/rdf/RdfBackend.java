package org.molgenis.downloader.rdf;

import com.google.common.io.Files;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.apache.commons.io.FileUtils.deleteQuietly;

public class RdfBackend implements AutoCloseable
{
	private static final Logger LOG = LoggerFactory.getLogger(RdfBackend.class);
	private static final String INDEXES = "spoc,posc,cosp";
	private final Repository repository;
	private final File storeDir;

	public RdfBackend()
	{
		storeDir = Files.createTempDir();
		LOG.debug("Creating native RDF repository in {}.", storeDir);
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
		LOG.debug("Closing native RDF repository...");
		try
		{
			repository.shutDown();
		}
		catch (Exception ex)
		{
			LOG.error("Failed to shut down repository.", ex);
			throw ex;
		}
		finally
		{
			if (!deleteQuietly(storeDir))
			{
				LOG.error("Failed to delete temporary repository directory.");
			}
		}

	}
}