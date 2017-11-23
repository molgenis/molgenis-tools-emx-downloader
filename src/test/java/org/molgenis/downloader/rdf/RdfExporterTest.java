package org.molgenis.downloader.rdf;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.mockito.*;
import org.mockito.quality.Strictness;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.MetadataRepository;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.rdf.ConnectionCallback;
import org.molgenis.rdf.RdfTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static org.eclipse.rdf4j.model.vocabulary.FOAF.NAMESPACE;
import static org.mockito.Mockito.*;

public class RdfExporterTest
{
	private RdfExporter rdfExporter;
	@Mock
	private MolgenisClient molgenisClient;
	@Mock
	private RdfTemplate rdfTemplate;
	@Mock
	private RepositoryConnection repositoryConnection;
	@Mock
	private EntityConsumerFactory consumerFactory;
	@Mock
	private EntityConsumer entityConsumer;
	@Mock
	private Entity samples;
	@Mock
	private Entity biobanks;
	@Mock
	private MolgenisVersion molgenisVersion;
	@Mock
	private MetadataRepository metadataRepository;
	@Mock
	private RdfConfig rdfConfig;
	@Captor
	private ArgumentCaptor<ConnectionCallback> connectionCallbackCaptor;
	private Integer pageSize = 101;
	private MockitoSession mockitoSession;

	@BeforeMethod
	public void beforeMethod() throws URISyntaxException
	{
		mockitoSession = Mockito.mockitoSession().initMocks(this).strictness(Strictness.STRICT_STUBS).startMocking();
		rdfExporter = new RdfExporter(molgenisClient, rdfTemplate, consumerFactory, rdfConfig);
	}

	@AfterMethod
	public void afterMethod()
	{
		mockitoSession.finishMocking();
	}

	@Test
	public void testExportData() throws Exception
	{
		doReturn(ImmutableMap.of(RDF.PREFIX, RDF.NAMESPACE, FOAF.PREFIX, NAMESPACE)).when(rdfConfig).getNamespaces();
		doReturn(metadataRepository).when(molgenisClient).getMetadata(molgenisVersion);
		doReturn(biobanks).when(metadataRepository).getEntity("bbmri_nl_biobanks");
		doReturn(samples).when(metadataRepository).getEntity("bbmri_nl_sample_collections");

		doNothing().when(rdfTemplate).execute(connectionCallbackCaptor.capture());
		doReturn("bbmri_nl_biobanks").when(biobanks).getFullName();
		doReturn("bbmri_nl_sample_collections").when(samples).getFullName();
		doReturn(entityConsumer).when(consumerFactory).create(biobanks, repositoryConnection, rdfConfig);
		doReturn(entityConsumer).when(consumerFactory).create(samples, repositoryConnection, rdfConfig);

		rdfExporter.exportData(ImmutableList.of("bbmri_nl_sample_collections", "bbmri_nl_biobanks"), pageSize,
				molgenisVersion);
		connectionCallbackCaptor.getAllValues().forEach(value -> value.doInConnection(repositoryConnection));

		verify(molgenisClient).streamEntityData("bbmri_nl_sample_collections", entityConsumer, pageSize);
		verify(molgenisClient).streamEntityData("bbmri_nl_biobanks", entityConsumer, pageSize);
		verify(repositoryConnection).setNamespace(RDF.PREFIX, RDF.NAMESPACE);
		verify(repositoryConnection).setNamespace(FOAF.PREFIX, NAMESPACE);
	}

	@Test
	public void testExportEntity() throws Exception
	{
		doNothing().when(rdfTemplate).execute(connectionCallbackCaptor.capture());
		when(samples.getFullName()).thenReturn("bbmri_nl_sample_collections");
		when(consumerFactory.create(samples, repositoryConnection, rdfConfig)).thenReturn(entityConsumer);

		rdfExporter.exportEntity(samples, pageSize);
		connectionCallbackCaptor.getValue().doInConnection(repositoryConnection);

		verify(molgenisClient).streamEntityData("bbmri_nl_sample_collections", entityConsumer, pageSize);
	}
}