package org.molgenis.downloader.util;

import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.api.metadata.Package;

import static org.molgenis.downloader.api.metadata.MolgenisVersion.VERSION_4;

public class NameUtils
{
	public static String getPackageFullName(Package pack, MolgenisVersion version)
	{
		if (pack == null) return "";

		String name = pack.getName();
		if (version != null && version.equalsOrLargerThan(VERSION_4)) return name;

		return pack.getParent() == null ? name : NameUtils.getPackageFullName(pack.getParent(), version) + "_" + name;
	}

	public static String getEntityShortName(Entity entity, MolgenisVersion version)
	{
		String prefix =
				entity.getPackage() == null ? "" : NameUtils.getPackageFullName(entity.getPackage(), version) + "_";
		return entity.getFullName().replaceFirst(prefix, "");
	}
}
