package org.molgenis.downloader.api.metadata;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class MolgenisVersionTest
{

	@Test
	public void testLargerThan()
	{
		final MolgenisVersion base = new MolgenisVersion(1, 0, 0);
		final MolgenisVersion largerRevision = new MolgenisVersion(1, 0, 1);
		final MolgenisVersion largerMinor = new MolgenisVersion(1, 1, 0);
		final MolgenisVersion largerMajor = new MolgenisVersion(2, 0, 0);
		assertTrue(largerRevision.largerThan(base), "result = " + largerRevision.compareTo(base));
		assertTrue(largerMinor.largerThan(base), "result = " + largerMinor.compareTo(base));
		assertTrue(largerMajor.largerThan(base), "result = " + largerMajor.compareTo(base));

	}

	@Test
	public void testComparable()
	{
		final MolgenisVersion base = new MolgenisVersion(1, 0, 0);
		final MolgenisVersion largerRevision = new MolgenisVersion(1, 0, 1);
		final MolgenisVersion largerMinor = new MolgenisVersion(1, 1, 0);
		final MolgenisVersion largerMajor = new MolgenisVersion(2, 0, 0);
		final List<MolgenisVersion> versions = new ArrayList<>();
		versions.add(largerRevision);
		versions.add(base);
		versions.add(largerMajor);
		versions.add(largerMinor);
		final List<MolgenisVersion> sorted = versions.stream().sorted().collect(Collectors.toList());
		assertTrue(sorted.get(0).equals(base));
		assertTrue(sorted.get(1).equals(largerRevision));
		assertTrue(sorted.get(2).equals(largerMinor));
		assertTrue(sorted.get(3).equals(largerMajor));
	}

	@Test
	public void testFrom()
	{
		assertEquals(MolgenisVersion.from("1.0.0"), new MolgenisVersion(1, 0, 0));
		assertNotEquals(MolgenisVersion.from("1.1.0"), new MolgenisVersion(1, 0, 0));
		assertEquals(MolgenisVersion.from("1.0"), new MolgenisVersion(1, 0, 0));
		assertEquals(MolgenisVersion.from("2.0.0-SNAPSHOT"), new MolgenisVersion(2, 0, 0));
	}
}
