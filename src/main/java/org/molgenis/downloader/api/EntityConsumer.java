package org.molgenis.downloader.api;

import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface EntityConsumer extends Consumer<Map<String, String>>
{
	default List<Attribute> getParts(final Attribute compound)
	{
		List<Attribute> atts = new ArrayList<>();
		compound.getParts().forEach((Attribute att) ->
		{
			if (att.getDataType().equals(DataType.COMPOUND))
			{
				atts.addAll(getParts(att));
			}
			else
			{
				atts.add(att);
			}
		});
		return atts;
	}

	default List<Attribute> getAttributes(final Entity entity)
	{
		List<Attribute> atts = new ArrayList<>();
		entity.getAttributes().forEach((Attribute att) ->
		{
			if (att.getDataType().equals(DataType.COMPOUND))
			{
				atts.addAll(getParts(att));
			}
			else
			{
				atts.add(att);
			}
		});
		return atts;
	}
}
