package org.molgenis.downloader.util;

import joptsimple.OptionParser;

import java.io.IOException;

public class ConsoleWriter
{
	public static void writeToConsole(String message, Exception e)
	{
		if (System.console() != null)
		{
			System.console().format(message + " %s\n", e.getLocalizedMessage()).flush();
		}
		else
		{
			System.out.print(message);
			if (e != null) e.printStackTrace();
			System.out.print("\n");
		}
	}

	public static void writeToConsole(String message)
	{
		writeToConsole(message, null);
	}

	public static void writeHelp(OptionParser optionParser) throws IOException
	{
		if (System.console() != null)
		{
			optionParser.printHelpOn(System.console().writer());
		}
		else
		{
			optionParser.printHelpOn(System.out);
		}

		writeToConsole(
				"Example: 'java -jar downloader.jar -f output.xls -a username -u molgenisserver.nl entity1 entity2 entity3]]'");
	}
}