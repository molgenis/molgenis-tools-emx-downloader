package org.molgenis.downloader.rdf;

import org.eclipse.rdf4j.rio.turtle.TurtleWriter;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.util.ConsoleWriter;
import org.molgenis.rdf.RdfTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class RdfClient
{
	private final MolgenisClient molgenisClient;
	private final RdfConfig rdfConfig;

	public RdfClient(MolgenisClient molgenisClient, RdfConfig rdfConfig)
	{
		this.molgenisClient = molgenisClient;
		this.rdfConfig = rdfConfig;
	}

	public void export(File outFile, List<String> entities, Integer pageSize, MolgenisVersion version)
	{
		try (RdfBackend backend = new RdfBackend(); FileOutputStream fos = new FileOutputStream(outFile))
		{
			RdfTemplate template = new RdfTemplate(backend.getRepository());
			RdfExporter exporter = new RdfExporter(molgenisClient, template, rdfConfig);
			exporter.exportData(entities, pageSize, version);
			template.execute(connection -> connection.export(new TurtleWriter(fos)));
		}
		catch (Exception ex)
		{
			ConsoleWriter.writeToConsole("Exception exporting RDF", ex);
		}
	}
}
