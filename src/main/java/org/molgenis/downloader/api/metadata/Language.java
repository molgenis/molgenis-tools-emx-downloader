package org.molgenis.downloader.api.metadata;

import java.util.Objects;

public class Language
{

	private final String code;
	private String name;
	private Boolean active;

	private Language(String code)
	{
		this.code = code;
	}

	public static Language from(final String code)
	{
		return new Language(code);
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 17 * hash + Objects.hashCode(this.code);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Language other = (Language) obj;
		return Objects.equals(this.code, other.code);
	}

}
