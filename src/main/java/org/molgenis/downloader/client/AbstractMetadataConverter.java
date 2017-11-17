package org.molgenis.downloader.client;

import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

abstract class AbstractMetadataConverter implements MetadataConverter
{

	void updateReference(final Entity ent, final Attribute att)
	{
		att.setEntityFullname(ent.getFullName());
		att.getParts().forEach((Attribute part) -> updateReference(ent, part));
	}

	String getString(final Map<String, String> data, final String field)
	{
		return data.get(field);
	}

	void setInteger(final Map<String, String> data, final String field, final Consumer<Integer> consumer)
	{
		setData(data, field, (s -> !s.isEmpty()), Integer::valueOf, consumer);
	}

	void setBoolean(final Map<String, String> data, final String field, final Consumer<Boolean> consumer)
	{
		setData(data, field, Boolean::valueOf, consumer);
	}

	void setString(final Map<String, String> data, final String field, final Consumer<String> consumer)
	{
		setData(data, field, (string -> !string.isEmpty()), (string -> string), consumer);
	}

	<R> void setData(final Map<String, String> data, final String field, Function<String, R> mapper,
			Consumer<R> consumer)
	{
		setData(data, field, (string -> !string.isEmpty()), mapper, consumer);
	}

	<R> void setList(final Map<String, String> data, final String field, Function<String, R> mapper,
			Consumer<R> consumer)
	{
		getList(data, field).ifPresent(value -> Arrays.stream(value).map(mapper).forEach(consumer));
	}

	private Optional<String[]> getList(final Map<String, String> data, final String field)
	{
		return Optional.ofNullable(data.get(field)).filter(string -> !string.isEmpty()).map(s -> s.split(","));
	}

	private <R> void setData(final Map<String, String> data, final String field, final Predicate<String> filter,
			final Function<String, R> mapper, final Consumer<R> consumer)
	{

		Optional.ofNullable(data.get(field)).filter(filter).map(mapper).ifPresent(consumer);
	}
}
