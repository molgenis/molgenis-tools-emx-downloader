package org.molgenis.downloader.emx.serializers;

import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.Tag;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EMXTagSerializer implements EntitySerializer<Tag>
{
	private static final String[] FIELDS = { "identifier", "label", "relationLabel", "objectIRI", "relationIRI",
			"codeSystem" };

	@Override
	public List<String> serialize(final Tag tag)
	{
		final List<String> fields = new ArrayList<>();
		fields.add(tag.getId());
		fields.add(tag.getLabel());
		fields.add(tag.getRelationLabel());
		fields.add(Optional.ofNullable(tag.getObjectIRI()).map(URI::toString).orElse(""));
		fields.add(Optional.ofNullable(tag.getRelationIRI()).map(URI::toString).orElse(""));
		fields.add(tag.getCodeSystem());
		return fields;
	}

	@Override
	public List<String> fields()
	{
		return Arrays.asList(FIELDS);
	}
}
