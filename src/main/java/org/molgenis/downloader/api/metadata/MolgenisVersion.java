package org.molgenis.downloader.api.metadata;

public class MolgenisVersion implements Comparable<MolgenisVersion>
{

	private final int major;
	private final int minor;
	private final int revison;

	public static final MolgenisVersion VERSION_2 = new MolgenisVersion(2, 0, 0);
	public static final MolgenisVersion VERSION_3 = new MolgenisVersion(3, 0, 0);
	public static final MolgenisVersion VERSION_4 = new MolgenisVersion(4, 0, 0);

	public MolgenisVersion(int major, int minor, int revison)
	{
		this.major = major;
		this.minor = minor;
		this.revison = revison;
	}

	public static MolgenisVersion from(final String version)
	{
		final String[] parts = version.split("\\.", 3);
		int major = Integer.parseInt(parts[0]);
		int minor = Integer.parseInt(parts[1]);
		if (parts.length == 3)
		{
			int revision = Integer.parseInt(parts[2].split("-")[0]);
			return new MolgenisVersion(major, minor, revision);
		}
		return new MolgenisVersion(major, minor, 0);
	}

	@Override
	public int compareTo(final MolgenisVersion that)
	{
		if (this.major != that.major)
		{
			return this.major - that.major;
		}
		if (this.minor != that.minor)
		{
			return this.minor - that.minor;
		}
		return this.revison - that.revison;
	}

	public boolean largerThan(final MolgenisVersion that)
	{
		return this.compareTo(that) > 0;
	}

	public boolean smallerThan(final MolgenisVersion that)
	{
		return this.compareTo(that) < 0;
	}

	public boolean equalsOrLargerThan(final MolgenisVersion otherVersion)
	{
		return this.equals(otherVersion) || this.largerThan(otherVersion);
	}

	public boolean equalsOrSmallerThan(final MolgenisVersion that)
	{
		return this.equals(that) || this.smallerThan(that);
	}

	public int getMajor()
	{
		return major;
	}

	public int getMinor()
	{
		return minor;
	}

	public int getRevison()
	{
		return revison;
	}

	@Override
	public String toString()
	{
		return "MolgenisVersion{" + "major=" + major + ", minor=" + minor + ", revison=" + revison + '}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof MolgenisVersion)) return false;

		MolgenisVersion that = (MolgenisVersion) o;

		if (major != that.major) return false;
		return minor == that.minor && revison == that.revison;
	}

	public boolean equalsMajor(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof MolgenisVersion)) return false;

		MolgenisVersion that = (MolgenisVersion) o;

		return major == that.major;
	}

	@Override
	public int hashCode()
	{
		int result = major;
		result = 31 * result + minor;
		result = 31 * result + revison;
		return result;
	}
}
