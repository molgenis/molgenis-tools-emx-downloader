package org.molgenis.downloader.api.metadata;

public enum DataType
{

	XREF, MREF, CATEGORICAL, CATEGORICAL_MREF, INT, STRING, TEXT, HYPERLINK, EMAIL, DATE_TIME, BOOL, COMPOUND, ENUM, LONG, SCRIPT, ONE_TO_MANY, DATE, DECIMAL, HTML, FILE;

	public boolean isXReferenceType()
	{
		return this == XREF || this == CATEGORICAL;
	}

	public boolean isMReferenceType()
	{
		return this == MREF || this == CATEGORICAL_MREF || this == ONE_TO_MANY;
	}

	public boolean isReferenceType()
	{
		return isMReferenceType() || isXReferenceType();
	}

	public boolean isNumericType()
	{
		return this == INT || this == LONG;
	}

	public static DataType from(final String text)
	{
		return DataType.valueOf(text.toUpperCase()
									.replaceFirst("CATEGORICALMREF", "CATEGORICAL_MREF")
									.replaceFirst("DATETIME", "DATE_TIME")
									.replaceFirst("ONETOMANY", "ONE_TO_MANY"));
	}
}
