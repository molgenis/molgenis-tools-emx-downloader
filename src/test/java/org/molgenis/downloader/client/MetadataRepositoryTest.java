
package org.molgenis.downloader.client;

import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class MetadataRepositoryTest {
    
    @Test
    public void testCreateNewAttribute() {
        MetadataRepository repository = new MetadataRepository();
        Attribute att = repository.createAttribute("test");
        assertNotNull(att);
        assertEquals(att.getId(), "test");
        assertTrue(repository.getAttributes().contains(att));
    }

    @Test
    public void testRetrieveExistingAttribute() {
        MetadataRepository repository = new MetadataRepository();
        repository.createAttribute("test");
        Attribute att = repository.createAttribute("test");
        assertNotNull(att);
        assertEquals(att.getId(), "test");
        assertTrue(repository.getAttributes().contains(att));
        assertTrue(repository.getAttributes().size() == 1);
    }

    @Test
    public void testUpdateExistingAttribute() {
        MetadataRepository repository = new MetadataRepository();
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
    public void testCreateNewEntity() {
        MetadataRepository repository = new MetadataRepository();
        Entity ent = repository.createEntity("test");
        assertNotNull(ent);
        assertEquals(ent.getFullName(), "test");
        assertTrue(repository.getEntities().contains(ent));
    }

    @Test
    public void testRetrieveExistingEntity() {
        MetadataRepository repository = new MetadataRepository();
        repository.createEntity("test");
        Entity ent = repository.createEntity("test");
        assertNotNull(ent);
        assertEquals(ent.getFullName(), "test");
        assertTrue(repository.getEntities().contains(ent));
        assertTrue(repository.getEntities().size() == 1);
    }

    @Test
    public void testEntityByName() {
        MetadataRepository repository = new MetadataRepository();
        repository.createEntity("test_entity");
        Entity ent = repository.createEntity("entity", "test");
        assertNotNull(ent);
        assertEquals(ent.getFullName(), "test_entity");
        assertTrue(repository.getEntities().contains(ent));
        assertTrue(repository.getEntities().size() == 1);
    }
    
    @Test
    public void testUpdateExistingEntity() {
        MetadataRepository repository = new MetadataRepository();
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
    public void testCreateNewPackage() {
        MetadataRepository repository = new MetadataRepository();
        Package pkg = repository.createPkg("test");
        assertNotNull(pkg);
        assertEquals(pkg.getName(), "test");
        assertTrue(repository.getPackages().contains(pkg));
    }

    @Test
    public void testRetrieveExistingPackage() {
        MetadataRepository repository = new MetadataRepository();
        repository.createPkg("test");
        Package pkg = repository.createPkg("test");
        assertNotNull(pkg);
        assertEquals(pkg.getName(), "test");
        assertTrue(repository.getPackages().contains(pkg));
        assertTrue(repository.getPackages().size() == 1);
    }

    @Test
    public void testUpdateExistingPackage() {
        MetadataRepository repository = new MetadataRepository();
        repository.createPkg("test");
        Package pkg1 = repository.createPkg("test");
        pkg1.setLabel("test");
        Package pkg2 = repository.createPkg("test");
        assertNotNull(pkg2);
        assertEquals(pkg2.getName(), "test");
        assertEquals(pkg2.getLabel(), "test");
        assertTrue(repository.getPackages().contains(pkg2));
        assertTrue(repository.getPackages().size() == 1);
    }
    @Test
    public void testCreateNewTag() {
        MetadataRepository repository = new MetadataRepository();
        Tag tag = repository.createTag("test");
        assertNotNull(tag);
        assertEquals(tag.getId(), "test");
        assertTrue(repository.getTags().contains(tag));
    }

    @Test
    public void testRetrieveExistingTag() {
        MetadataRepository repository = new MetadataRepository();
        repository.createTag("test");
        Tag tag = repository.createTag("test");
        assertNotNull(tag);
        assertEquals(tag.getId(), "test");
        assertTrue(repository.getTags().contains(tag));
        assertTrue(repository.getTags().size() == 1);
    }

    @Test
    public void testUpdateExistingTag() {
        MetadataRepository repository = new MetadataRepository();
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
