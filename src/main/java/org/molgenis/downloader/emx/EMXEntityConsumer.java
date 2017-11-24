package org.molgenis.downloader.emx;

import org.molgenis.downloader.api.EMXDataStore;
import org.molgenis.downloader.api.EMXWriter;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class EMXEntityConsumer implements EntityConsumer
{
	private final List<Attribute> attributes;
	private final EMXDataStore sheet;
	private final EMXWriter writer;

	EMXEntityConsumer(final EMXWriter writer, final Entity entity) throws IOException
	{
		this.writer = writer;
		attributes = getAttributes(entity);
		final List<String> values = getAttributes().stream().map(Attribute::getName).collect(Collectors.toList());

		sheet = writer.createDataStore(entity.getFullName());
		sheet.writeRow(values);
	}

	@Override
	public void accept(Map<String, String> data)
	{
		final List<String> values = new ArrayList<>();
		for (int index = 0; index < getAttributeNames().size(); index++)
		{
			final String value = data.get(getAttributeNames().get(index));
			if (value != null && !value.trim().isEmpty())
			{
				values.add(value.trim());
			}
			else
			{
				values.add(null);
			}
		}
		try
		{
			sheet.writeRow(values);
		}
		catch (final IOException ex)
		{
			writer.addException(ex);
		}
	}

	private List<Attribute> getAttributes()
	{
		return attributes;
	}

	private List<String> getAttributeNames()
	{
		return attributes.stream().map(Attribute::getName).collect(Collectors.toList());
	}

}
