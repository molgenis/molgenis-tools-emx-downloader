package org.molgenis.downloader.client;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.MetadataConsumer;
import org.molgenis.downloader.api.MolgenisClient;
import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.MolgenisVersion;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.molgenis.downloader.util.ConsoleWriter.writeToConsole;

public class MolgenisRestApiClient implements MolgenisClient
{

	private static final MolgenisVersion VERSION_2 = new MolgenisVersion(2, 0, 0);
	private final HttpClient client;
	private final WriteableMetadataRepository repository = new MetadataRepositoryImpl();
	;
	private MetadataConverter converter;
	private final URI uri;
	private String token;

	public MolgenisRestApiClient(final HttpClient client, final URI uri) throws IOException, URISyntaxException
	{
		this.client = client;
		this.uri = uri;
	}

	@Override
	public final void login(final String username, final String password)
	{
		final JSONObject login = new JSONObject();
		login.put("username", username);
		login.put("password", password);
		JSONObject response;
		token = null;
		try
		{
			final HttpPost request = new HttpPost(new URI(uri + "/api/v1/login"));
			request.setHeader("Content-Type", "application/json");
			request.setEntity(new StringEntity(login.toString()));

			final HttpResponse result = client.execute(request);
			if (result.getStatusLine().getStatusCode() == 200)
			{
				response = new JSONObject(EntityUtils.toString(result.getEntity()));
				token = response.getString("token");
			}
		}
		catch (final JSONException | IOException | URISyntaxException ex)
		{
			writeToConsole("An error occurred while logging in: %s.\n", ex);
		}
	}

	@Override
	public final boolean logout()
	{
		HttpGet request = new HttpGet(uri + "/api/v1/logout");
		if (token != null)
		{
			request.setHeader("x-molgenis-token", token);
		}
		try
		{
			return client.execute(request).getStatusLine().getStatusCode() == 200;
		}
		catch (IOException ex)
		{
			writeToConsole("An error occurred while logging out: %s.\n", ex);
			return false;
		}
	}

	@Override
	public final MolgenisVersion getVersion() throws IOException, URISyntaxException
	{
		final String data = download(new URI(uri + "/api/v2/getVersion"));
		final JSONObject json = new JSONObject(data);
		final String version = json.getString("molgenisVersion");

		return MolgenisVersion.from(version);
	}

	@Override
	public Entity getEntity(final String name) throws IOException, URISyntaxException
	{
		final JSONObject json = getJsonDataFromUrl(uri + "/api/v2/" + name);
		final JSONObject meta = json.getJSONObject("meta");
		return entityFromJSON(meta);
	}

	@Override
	public final void streamEntityData(final String name, final EntityConsumer consumer)
	{
		try
		{
			JSONObject json = getJsonDataFromUrl(uri + "/api/v2/" + name);
			final JSONObject meta = json.getJSONObject("meta");
			final Entity entity = entityFromJSON(meta);

			String nextHref;
			do
			{
				final JSONArray items = json.getJSONArray("items");
				items.iterator().forEachRemaining(
						(Object item) -> consumer.accept(getAttributes((JSONObject) item, entity.getAttributes())));

				nextHref = json.optString("nextHref");
				if (notNullOrEmpty(nextHref))
				{
					json = getJsonDataFromUrl(nextHref);
				}
			}
			while (notNullOrEmpty(nextHref));

		}
		catch (final JSONException | IOException | URISyntaxException | ParseException ex)
		{
			Logger.getLogger(MolgenisRestApiClient.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private JSONObject getJsonDataFromUrl(String url) throws IOException, URISyntaxException
	{
		JSONObject json;
		String nextData = download(new URI(url));
		json = new JSONObject(nextData);
		return json;
	}

	@Override
	public void getMetaData(MetadataConsumer consumer)
	{
		if(converter == null) initConverter();
		streamEntityData(converter.getLanguagesRepository(), converter::toLanguage);
		streamEntityData(converter.getTagsRepository(), converter::toTag);
		streamEntityData(converter.getPackagesRepository(), converter::toPackage);
		streamEntityData(converter.getAttributesRepository(), converter::toAttribute);
		streamEntityData(converter.getEnitiesRepository(), converter::toEntity);
		consumer.accept(repository);
	}

	@Override
	public void close() throws Exception
	{
		try
		{
			if (token != null)
			{
				logout();
			}
		}
		finally
		{
			token = null;
		}
	}

	private String download(final URI uri) throws JSONException, IOException, ParseException
	{
		HttpGet request = new HttpGet(uri);
		if (token != null)
		{
			request.setHeader("x-molgenis-token", token);
		}
		HttpResponse result = client.execute(request);
		String data = EntityUtils.toString(result.getEntity(), StandardCharsets.UTF_8);
		return data;
	}

	private boolean notNullOrEmpty(final String string)
	{
		return string != null && !"".equals(string);
	}

	private Map<Attribute, String> getAttributes(final JSONObject input, Collection<Attribute> attributes)
	{
		final Map<Attribute, String> data = new HashMap<>();

		attributes.forEach((Attribute attribute) ->
		{

			final DataType type = attribute.getDataType();
			final String name = attribute.getName();

			if (type.isXReferenceType())
			{
				final JSONObject reference = input.optJSONObject(name);
				if (reference != null)
				{
					final Entity refEntity = attribute.getRefEntity();
					final String id = refEntity.getIdAttribute().getName();
					if (refEntity.getIdAttribute().getDataType().isNumericType())
					{
						data.put(attribute, Long.toString(reference.getLong(id)));
					}
					else
					{
						data.put(attribute, reference.getString(id));
					}
				}
			}
			else if (type.isMReferenceType())
			{
				final JSONArray array = input.optJSONArray(name);
				if (array != null)
				{
					final Entity refEntity = attribute.getRefEntity();
					final String id = refEntity.getIdAttribute().getName();
					final List<String> elements = new ArrayList<>();
					array.forEach((Object element) ->
					{
						final JSONObject reference = (JSONObject) element;
						if (refEntity.getIdAttribute().getDataType().isNumericType())
						{
							elements.add(Long.toString(reference.getLong(id)));
						}
						else
						{
							elements.add(reference.getString(id));
						}
					});
					final String references = elements.stream().collect(Collectors.joining(","));
					data.put(attribute, references);
				}
			}
			else if (type.equals(DataType.COMPOUND))
			{
				Map<Attribute, String> parts = getAttributes(input, attribute.getParts());
				data.putAll(parts);
			}
			else
			{
				final String value = input.optString(name);
				data.put(attribute, value);
			}
		});
		return data;
	}

	private Entity entityFromJSON(final JSONObject metadata)
	{
		Entity ent = new Entity(metadata.getString("name"));
		final String idAttribute = metadata.getString("idAttribute");
		final JSONArray attributes = metadata.getJSONArray("attributes");
		attributes.iterator().forEachRemaining((Object object) ->
		{
			final JSONObject attributeMetadata = (JSONObject) object;
			final Attribute attribute = attributeFromJSON(ent, attributeMetadata);
			ent.addAttribute(attribute);
			if (idAttribute.equals(attribute.getName()))
			{
				ent.setIdAttribute(attribute);
			}
		});
		return ent;
	}

	private Attribute attributeFromJSON(final Entity entity, final JSONObject meta) throws JSONException
	{
		final DataType type = DataType.valueOf(meta.getString("fieldType"));
		final String name = meta.getString("name");
		Attribute att = new Attribute(name);
		att.setEntity(entity);
		att.setName(name);
		att.setDataType(type);

		if (DataType.COMPOUND.equals(type))
		{
			final JSONArray parts = meta.getJSONArray("attributes");
			parts.forEach(part ->
			{
				final JSONObject partMeta = (JSONObject) part;
				att.addPart(attributeFromJSON(entity, partMeta));
			});
		}
		else
		{
			final boolean nillable = meta.getBoolean("nillable");
			att.setNilleble(nillable);
			if (type.isReferenceType())
			{
				final JSONObject refEntity = meta.getJSONObject("refEntity");
				att.setRefEntity(entityFromJSON(refEntity));
			}
		}
		return att;
	}

	private void initConverter()
	{
		if (converter == null)
		{
			final MolgenisVersion version;
			try
			{
				version = getVersion();
				converter = version.smallerThan(VERSION_2) ? new MolgenisV1MetadataConverter(
						repository) : new MolgenisV2MetadataConverter(repository);
			}
			catch (IOException e)
			{
				//FIXME
				e.printStackTrace();
			}
			catch (URISyntaxException e)
			{
				//FIXME
				e.printStackTrace();
			}
		}
	}
}
