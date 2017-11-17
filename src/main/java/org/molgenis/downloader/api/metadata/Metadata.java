package org.molgenis.downloader.api.metadata;

public abstract class Metadata
{
	String id;

	public String getId()
	{
		return id;
	}

	public Metadata setId(String id)
	{
		this.id = id;
		return this;
	}

}
