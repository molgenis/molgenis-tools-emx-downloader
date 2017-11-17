package org.molgenis.downloader.client;

import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MetadataRepositoryImplTest
{

	@Test
	public void testCreateNewAttribute()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		Attribute att = repository.createAttribute("test");
		assertNotNull(att);
		assertEquals(att.getId(), "test");
		assertTrue(repository.getAttributes().contains(att));
	}

	@Test
	public void testRetrieveExistingAttribute()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createAttribute("test");
		Attribute att = repository.createAttribute("test");
		assertNotNull(att);
		assertEquals(att.getId(), "test");
		assertTrue(repository.getAttributes().contains(att));
		assertTrue(repository.getAttributes().size() == 1);
	}

	@Test
	public void testUpdateExistingAttribute()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createAttribute("test");
		Attribute att1 = repository.createAttribute("test");
		att1.setLabel("test");
		Attribute att2 = repository.createAttribute("test");
		assertNotNull(att2);
		assertEquals(att2.getId(), "test");
		assertEquals(att2.getLabel(), "test");
		assertTrue(repository.getAttributes().contains(att2));
		assertTrue(repository.getAttributes().size() == 1);
	}

	@Test
	public void testCreateNewEntity()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		Entity ent = repository.createEntity("test");
		assertNotNull(ent);
		assertEquals(ent.getFullName(), "test");
		assertTrue(repository.getEntities().contains(ent));
	}

	@Test
	public void testRetrieveExistingEntity()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createEntity("test");
		Entity ent = repository.createEntity("test");
		assertNotNull(ent);
		assertEquals(ent.getFullName(), "test");
		assertTrue(repository.getEntities().contains(ent));
		assertTrue(repository.getEntities().size() == 1);
	}

	@Test
	public void testEntityByName()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createEntity("test_entity");
		Entity ent = repository.createEntity("entity", "test");
		assertNotNull(ent);
		assertEquals(ent.getFullName(), "test_entity");
		assertTrue(repository.getEntities().contains(ent));
		assertTrue(repository.getEntities().size() == 1);
	}

	@Test
	public void testUpdateExistingEntity()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createEntity("test");
		Entity ent1 = repository.createEntity("test");
		ent1.setLabel("test");
		Entity ent2 = repository.createEntity("test");
		assertNotNull(ent2);
		assertEquals(ent2.getFullName(), "test");
		assertEquals(ent2.getLabel(), "test");
		assertTrue(repository.getEntities().contains(ent2));
		assertTrue(repository.getEntities().size() == 1);
	}

	@Test
	public void testCreateNewPackage()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		Package pkg = repository.createPackage("test");
		assertNotNull(pkg);
		assertEquals(pkg.getName(), "test");
		assertTrue(repository.getPackages().contains(pkg));
	}

	@Test
	public void testRetrieveExistingPackage()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createPackage("test");
		Package pkg = repository.createPackage("test");
		assertNotNull(pkg);
		assertEquals(pkg.getName(), "test");
		assertTrue(repository.getPackages().contains(pkg));
		assertTrue(repository.getPackages().size() == 1);
	}

	@Test
	public void testUpdateExistingPackage()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createPackage("test");
		Package pkg1 = repository.createPackage("test");
		pkg1.setLabel("test");
		Package pkg2 = repository.createPackage("test");
		assertNotNull(pkg2);
		assertEquals(pkg2.getName(), "test");
		assertEquals(pkg2.getLabel(), "test");
		assertTrue(repository.getPackages().contains(pkg2));
		assertTrue(repository.getPackages().size() == 1);
	}

	@Test
	public void testCreateNewTag()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		Tag tag = repository.createTag("test");
		assertNotNull(tag);
		assertEquals(tag.getId(), "test");
		assertTrue(repository.getTags().contains(tag));
	}

	@Test
	public void testRetrieveExistingTag()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createTag("test");
		Tag tag = repository.createTag("test");
		assertNotNull(tag);
		assertEquals(tag.getId(), "test");
		assertTrue(repository.getTags().contains(tag));
		assertTrue(repository.getTags().size() == 1);
	}

	@Test
	public void testUpdateExistingTag()
	{
		MetadataRepositoryImpl repository = new MetadataRepositoryImpl();
		repository.createTag("test");
		Tag tag1 = repository.createTag("test");
		tag1.setLabel("test");
		Tag tag2 = repository.createTag("test");
		assertNotNull(tag2);
		assertEquals(tag2.getId(), "test");
		assertEquals(tag2.getLabel(), "test");
		assertTrue(repository.getTags().contains(tag2));
		assertTrue(repository.getTags().size() == 1);
	}

}
