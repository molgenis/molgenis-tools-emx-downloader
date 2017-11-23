package org.molgenis.downloader.rdf;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class RdfConfigImpl implements RdfConfig
{
	static final String DEFAULT_NAMESPACE = "http://molgenis.org#";
	static final String DEFAULT_NAMESPACE_PREFIX = "mlg";
	private Map<String, String> namespaces;
	private String defaultNamespace;
	private String defaultNamespacePrefix;

	public RdfConfigImpl()
	{
		namespaces = loadDefaultNamespaces();
		defaultNamespace = DEFAULT_NAMESPACE;
		defaultNamespacePrefix = DEFAULT_NAMESPACE_PREFIX;
	}

	private static Map<String, String> loadDefaultNamespaces()
	{
		return getPropertiesMap(RdfConfig.class.getResourceAsStream("namespaces.properties"));
	}

	private static Map<String, String> getPropertiesMap(InputStream inputStream)
	{
		Properties properties = new Properties();
		try
		{
			properties.load(inputStream);
		}
		catch (IOException ignore)
		{
		}
		return Maps.fromProperties(properties);
	}

	public void loadAdditionalNamespaces(File file) throws FileNotFoundException
	{
		Map<String, String> additionalNamespaces = getPropertiesMap(new FileInputStream(file));
		namespaces.putAll(additionalNamespaces);
	}

	@Override
	public Map<String, String> getNamespaces()
	{
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.putAll(namespaces);
		builder.put(defaultNamespacePrefix, defaultNamespace);
		return builder.build();
	}

	@Override
	public String getDefaultNamespace()
	{
		return defaultNamespace;
	}

	public void setDefaultNamespace(String prefix, String namespace)
	{
		this.defaultNamespacePrefix = prefix;
		this.defaultNamespace = namespace;
	}
}
