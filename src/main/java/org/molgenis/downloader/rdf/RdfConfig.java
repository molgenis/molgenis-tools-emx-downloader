package org.molgenis.downloader.rdf;

import java.util.Map;

public interface RdfConfig
{
	/**
	 * @return the default namespace to use for the molgenis IRI generation.
	 */
	String getDefaultNamespace();

	/**
	 * @return map mapping prefix to namespace, including the default namespace
	 */
	Map<String, String> getNamespaces();
}
