package org.molgenis.rdf;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.STRICT_STUBS;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

public class RdfTemplateTest
{
	private RdfTemplate rdfTemplate;
	@Mock
	private Repository repository;
	@Mock
	private ConnectionCallback connectionCallback;
	@Mock
	private RepositoryConnection connection;
	private MockitoSession mockitoSession;

	@BeforeMethod
	public void beforeMethod()
	{
		mockitoSession = Mockito.mockitoSession().initMocks(this).strictness(STRICT_STUBS).startMocking();
		rdfTemplate = new RdfTemplate(repository);
	}

	@AfterMethod
	public void afterMethod()
	{
		mockitoSession.finishMocking();
	}

	@Test
	public void testExecute()
	{
		when(repository.getConnection()).thenReturn(connection);
		rdfTemplate.execute(connectionCallback);
		verify(connectionCallback).doInConnection(connection);
		verify(connection).close();
	}

	@Test
	public void testExecuteException()
	{
		RepositoryException repositoryException = new RepositoryException();
		doThrow(repositoryException).when(connectionCallback).doInConnection(connection);
		when(repository.getConnection()).thenReturn(connection);
		try
		{
			rdfTemplate.execute(connectionCallback);
			fail("Should throw exception");
		}
		catch (RepositoryException expected)
		{
			assertSame(expected, repositoryException);
		}
		verify(connection).close();
		verify(connectionCallback).doInConnection(connection);
	}
}