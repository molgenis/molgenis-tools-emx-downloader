package org.molgenis.downloader.rdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.eclipse.rdf4j.rio.turtle.TurtleWriter;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.rdf.RdfTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RdfClient {
  private static final Logger LOG = LoggerFactory.getLogger(RdfClient.class);
  private final MolgenisClient molgenisClient;
  private final RdfConfig rdfConfig;

  public RdfClient(MolgenisClient molgenisClient, RdfConfig rdfConfig) {
    this.molgenisClient = molgenisClient;
    this.rdfConfig = rdfConfig;
  }

  public void export(
      File outFile, List<String> entities, Integer pageSize, MolgenisVersion version) {
    try (RdfBackend backend = new RdfBackend();
        FileOutputStream fos = new FileOutputStream(outFile)) {
      RdfTemplate template = new RdfTemplate(backend.getRepository());
      RdfExporter exporter = new RdfExporter(molgenisClient, template, rdfConfig);
      exporter.exportData(entities, pageSize, version);
      LOG.info("Exporting from local repository in turtle format to {}...", outFile);
      template.execute(connection -> connection.export(new TurtleWriter(fos)));
    } catch (Exception ex) {
      LOG.error("Exception exporting RDF", ex);
    }
  }
}
