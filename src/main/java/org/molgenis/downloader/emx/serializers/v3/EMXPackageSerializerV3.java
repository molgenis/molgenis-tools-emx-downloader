package org.molgenis.downloader.emx.serializers.v3;

import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.MolgenisVersion;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;
import org.molgenis.downloader.util.NameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class EMXPackageSerializerV3 implements EntitySerializer<Package>
{
	private static final String[] FIELDS = { "name", "description", "label", "parent", "tags" };

	private final MolgenisVersion version;

	public EMXPackageSerializerV3(MolgenisVersion version)
	{
		this.version = version;
	}

	@Override
	public List<String> serialize(final Package pkg)
	{
		final List<String> result = new ArrayList<>();
		result.add(NameUtils.getPackageFullName(pkg, version));
		result.add(pkg.getDescription());
		result.add(pkg.getLabel());
		result.add(NameUtils.getPackageFullName(pkg.getParent(), version));
		result.add(pkg.getTags().stream().map(Tag::getId).collect(joining(",")));
		return result;
	}

	@Override
	public List<String> fields()
	{
		return Arrays.asList(FIELDS);
	}

}
