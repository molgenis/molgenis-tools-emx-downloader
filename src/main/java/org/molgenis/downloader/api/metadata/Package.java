package org.molgenis.downloader.api.metadata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Package extends Metadata
{
	private String name;
	private String description;
	private Package parent;
	private final Set<Tag> tags;
	private String label;

	public Package(final String name)
	{
		tags = new HashSet<>();
		this.name = name;
	}

	public Package()
	{
		tags = new HashSet<>();
	}

	public static Package from(final String fullName)
	{
		if (fullName == null || fullName.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		return new Package(fullName);
	}

	public static Package fromId(final String id)
	{
		if (id == null || id.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		Package pack = new Package();
		pack.setId(id);
		return pack;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Package)) return false;

		Package aPackage = (Package) o;

		if (name != null ? !name.equals(aPackage.name) : aPackage.name != null) return false;
		if (description != null ? !description.equals(aPackage.description) : aPackage.description != null)
			return false;
		if (parent != null ? !parent.equals(aPackage.parent) : aPackage.parent != null) return false;
		if (tags != null ? !tags.equals(aPackage.tags) : aPackage.tags != null) return false;
		return label != null ? label.equals(aPackage.label) : aPackage.label == null;
	}

	@Override
	public int hashCode()
	{
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (parent != null ? parent.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		result = 31 * result + (label != null ? label.hashCode() : 0);
		return result;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public Set<Tag> getTags()
	{
		return Collections.unmodifiableSet(tags);
	}

	public String getDescription()
	{
		return description;
	}

	public Package getParent()
	{
		return parent;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setParent(Package parent)
	{
		this.parent = parent;
	}

	public void addTag(final Tag tag)
	{
		tags.add(tag);
	}

	@Override
	public String toString()
	{
		return "Package{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", parent=" + parent
				+ ", tags=" + tags + ", label='" + label + '\'' + '}';
	}
}
