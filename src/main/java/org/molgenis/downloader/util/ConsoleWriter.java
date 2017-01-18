package org.molgenis.downloader.util;

public class ConsoleWriter
{
	public static void writeToConsole(String message, Exception e)
	{
		if (System.console() != null)
		{
			System.console().format(message, e.getLocalizedMessage()).flush();
		}
		else
		{
			System.out.print(message);
			if (e != null) System.err.print(e.getLocalizedMessage());
			System.out.print("\n");
		}
	}

	public static void writeToConsole(String message)
	{
		writeToConsole(message, null);
	}
}