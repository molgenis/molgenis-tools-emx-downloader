package org.molgenis.downloader.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class GNpreprocessor
{

	private static Map<String, Map<String, Double>> resultMap;
	private static long now;

	public static void main(String[] args)
	{
		System.out.print("Start loading!");
		now = new Date().getTime();
		System.out.println(new Date().getTime());
		String inputFile = "/Users/charbonb/Downloads/GeneNetwork.txt";
		String mapping = "/Users/charbonb/Downloads/mart_export.txt";
		try
		{
			resultMap = importScores(inputFile, mapping);
			System.out.println("Done loading!");
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println(
					"Memory usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println("getScore(\"COL7A1\",\"HP_0000505\"):" + getScore("COL7A1", "HP_0000505"));
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println("getScore(\"TTN\",\"HP_0005607\"):" + getScore("TTN", "HP_0005607"));
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println("getScore(\"CHD7\",\"HP_3000050\"):" + getScore("CHD7", "HP_3000050"));
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println("getScore(\"BRCA1\",\"HP_0100540\"):" + getScore("BRCA1", "HP_0100540"));
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println("getScore(\"TTN\",\"HP_0100037\"):" + getScore("TTN", "HP_0100037"));
			System.out.println("time passed: " + (new Date().getTime() - now));
			System.out.println("getScore(\"TTN\",\"HP_0012072\"):" + getScore("TTN", "HP_0012072"));
			System.out.println("time passed: " + (new Date().getTime() - now));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static Double getScore(String gene, String hpo)
	{
		return resultMap.get(gene).get(hpo);
	}

	public static Map<String, Map<String, Double>> importScores(String geneNetworkFile, String geneMappingFile)
			throws IOException
	{
		Map<String, Map<String, Double>> result = new HashMap<>();
		try
		{
			File input = new File(geneNetworkFile);
			Scanner scanner = new Scanner(input, "UTF-8");
			Scanner hpoScanner = new Scanner(scanner.nextLine());
			List<String> hpoTerms = createHpoTermList(hpoScanner);

			Map<String, String> geneMap = createEnsembleHugoMap(geneMappingFile);
			int rowNr = 0;
			while (scanner.hasNext())
			{
				processSingleInputLine(scanner, hpoTerms, geneMap, result);
				rowNr++;
				if (rowNr % 1000 == 0)
				{
					System.out.println("loaded " + rowNr + " rows");
					System.out.println("time passed: " + (new Date().getTime() - now));
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	protected static void processSingleInputLine(Scanner scanner, List<String> hpoTerms, Map<String, String> geneMap,
			Map<String, Map<String, Double>> result) throws IOException
	{
		Scanner rowScanner = new Scanner(scanner.nextLine());
		String gene = null;
		Map<String, Double> singleLineResultMap = new HashMap<>();
		if (rowScanner.hasNext()) gene = rowScanner.next();
		int i = 0;

		if (geneMap.get(gene) != null)
		{
			String hugo = geneMap.get(gene);
			if (hugo != null)
			{
				{
					while (rowScanner.hasNext())
					{
						createEntities(hpoTerms.get(i), rowScanner, singleLineResultMap);
						++i;
					}
				}
			}
			result.put(hugo,singleLineResultMap);
		}

	}

	protected static List<String> createHpoTermList(Scanner hpoScanner)
	{
		List<String> hpoTerms = new ArrayList<>();
		hpoScanner.next();
		while (hpoScanner.hasNext())
		{
			hpoTerms.add(hpoScanner.next());
		}
		return hpoTerms;
	}

	protected static Map<String, Double> createEntities(String hpoTerm, Scanner rowScanner, Map<String, Double> singleLineResultMap)
			throws IOException
	{

		singleLineResultMap.put(hpoTerm, Double.parseDouble(rowScanner.next()));
		return singleLineResultMap;
	}

	protected static Map<String, String> createEnsembleHugoMap(String geneMappingFilePath) throws FileNotFoundException
	{
		File geneMappingFile = new File(geneMappingFilePath);
		Scanner geneMappingScanner = new Scanner(geneMappingFile, "UTF-8");
		Map<String, String> geneMap = new HashMap<>();
		geneMappingScanner.nextLine();//skip header
		while (geneMappingScanner.hasNext())
		{
			String hugo = "";
			String ensembl;
			Scanner geneScanner = new Scanner(geneMappingScanner.nextLine());
			if (geneScanner.hasNext()) ensembl = geneScanner.next();
			else throw new RuntimeException("every line should have at lease an ensembl ID");
			if (geneScanner.hasNext()) hugo = geneScanner.next();
			geneMap.put(ensembl, hugo);
		}
		return geneMap;
	}
}
