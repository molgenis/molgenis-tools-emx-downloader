package org.molgenis.downloader.rdf;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Tag;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.molgenis.downloader.api.metadata.DataType.*;
import static org.molgenis.downloader.rdf.RdfConfigImpl.DEFAULT_NAMESPACE;
import static org.molgenis.downloader.rdf.RdfEntityConsumer.DATATYPE_FACTORY;
import static org.molgenis.downloader.rdf.RdfEntityConsumer.IS_ASSOCIATED_WITH_URI;
import static org.testng.Assert.assertEquals;

public class RdfEntityConsumerTest
{
	private Entity entityType;
	private Entity refEntityType;
	private Tag labelTag;
	private ValueFactory valueFactory;
	private RdfConfig rdfConfig;

	@BeforeClass
	public void setupBeforeClass() throws URISyntaxException
	{
		valueFactory = SimpleValueFactory.getInstance();

		labelTag = new Tag("test");
		labelTag.setObjectIRI("test:label");
		labelTag.setRelationIRI(IS_ASSOCIATED_WITH_URI);

		refEntityType = new Entity("ref");
		refEntityType.setId("ref");

		rdfConfig = new RdfConfigImpl();
	}

	@BeforeMethod
	public void setupBeforeMethod()
	{
		Attribute idAttribute = new Attribute("id");
		idAttribute.setName("id");
		idAttribute.setIdAttribute(true);

		entityType = new Entity("test");
		entityType.addAttribute(idAttribute);
		entityType.setIdAttribute(idAttribute);
	}

	@DataProvider(name = "provideConversionData")
	public Iterator<Object[]> provideConversionData()
	{
		List<Object[]> provider = new ArrayList<>();

		IRI subject = valueFactory.createIRI(DEFAULT_NAMESPACE + "test/1");
		IRI predicate = valueFactory.createIRI(labelTag.getObjectIRI());

		{ // BOOL
			Literal object = valueFactory.createLiteral(true);
			provider.add(new Object[] { createAttribute("boolAttr", BOOL), createData("boolAttr", "true"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // DATE
			Literal object = valueFactory.createLiteral(
					DATATYPE_FACTORY.newXMLGregorianCalendar(LocalDate.parse("2014-08-01").toString()));
			provider.add(new Object[] { createAttribute("dateAttr", DATE), createData("dateAttr", "2014-08-01"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // DATE_TIME
			Literal object = valueFactory.createLiteral(
					DATATYPE_FACTORY.newXMLGregorianCalendar(Instant.parse("1985-08-12T08:12:13Z").toString()));
			provider.add(new Object[] { createAttribute("datetimeAttr", DATE_TIME),
					createData("datetimeAttr", "1985-08-12T08:12:13Z"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // DECIMAL
			Literal object = valueFactory.createLiteral(1.23);
			provider.add(new Object[] { createAttribute("decimalAttr", DECIMAL), createData("decimalAttr", "1.23"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // LONG
			Literal object = valueFactory.createLiteral(2147483648L);
			provider.add(new Object[] { createAttribute("longAttr", LONG), createData("longAttr", "2147483648"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // INT
			Literal object = valueFactory.createLiteral(8);
			provider.add(new Object[] { createAttribute("intAttr", INT), createData("intAttr", "8"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // ENUM, HTML, TEXT, SCRIPT
			Literal object = valueFactory.createLiteral("enum");
			provider.add(new Object[] { createAttribute("enumAttr", ENUM), createData("enumAttr", "enum"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // STRING
			Literal object = valueFactory.createLiteral("test");
			provider.add(new Object[] { createAttribute("stringAttr", STRING), createData("stringAttr", "test"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // EMAIL
			IRI object = valueFactory.createIRI("mailto:test@molgenis.nl");
			provider.add(
					new Object[] { createAttribute("emailAttr", EMAIL), createData("emailAttr", "test@molgenis.nl"),
							createSingleStatement(subject, predicate, object) });
		}
		{ // HYPERLINK
			IRI object = valueFactory.createIRI("http://test.nl");
			provider.add(new Object[] { createAttribute("hyperlinkAttr", HYPERLINK),
					createData("hyperlinkAttr", "http://test.nl"), createSingleStatement(subject, predicate, object) });
		}
		{ // XREF
			IRI object = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/1");
			provider.add(new Object[] { createRefAttribute("xrefAttr", XREF), createData("xrefAttr", "1"),
					createSingleStatement(subject, predicate, object) });
		}
		{ // CATEGORICAL
			IRI object = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/1");
			provider.add(new Object[] { createRefAttribute("categoricalAttr", CATEGORICAL),
					createData("categoricalAttr", "1"), createSingleStatement(subject, predicate, object) });
		}
		{ // MREF
			IRI object1 = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/1");
			IRI object2 = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/2");
			IRI object3 = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/3");
			provider.add(new Object[] { createRefAttribute("mrefAttr", MREF), createData("mrefAttr", "1,2,3"),
					createMultipleStatements(subject, predicate, asList(object1, object2, object3)) });
		}
		{ // CATEGORICAL_MREF
			IRI object1 = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/1");
			IRI object2 = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/2");
			IRI object3 = valueFactory.createIRI(DEFAULT_NAMESPACE + "ref/3");
			provider.add(new Object[] { createRefAttribute("categoricalmrefAttr", CATEGORICAL_MREF),
					createData("categoricalmrefAttr", "1,2,3"),
					createMultipleStatements(subject, predicate, asList(object1, object2, object3)) });
		}

		return provider.iterator();
	}

	@Test(dataProvider = "provideConversionData")
	public void testDataConversion(Attribute attribute, Map<String, String> data, List<Statement> expectedStatements)
	{
		entityType.addAttribute(attribute);

		List<Statement> actualStatements = runEntityConsumer(data);

		assertEquals(actualStatements, expectedStatements);
	}

	private List<Statement> runEntityConsumer(Map<String, String> data)
	{
		List<Statement> actualStatements = new ArrayList<>();
		EntityConsumer rdfEntityConsumer = new RdfEntityConsumer(entityType, valueFactory, actualStatements::add,
				rdfConfig);
		rdfEntityConsumer.accept(data);
		return actualStatements;
	}

	private Attribute createAttribute(String name, DataType dataType)
	{
		Attribute attribute = Attribute.createAttribute(name, name);
		attribute.setDataType(dataType);
		attribute.addTag(labelTag);
		return attribute;
	}

	private Attribute createRefAttribute(String name, DataType dataType)
	{
		Attribute attribute = createAttribute(name, dataType);
		attribute.setRefEntity(refEntityType);
		return attribute;
	}

	private Map<String, String> createData(String name, String value)
	{
		Map<String, String> data = new HashMap<>();
		data.put("id", "1");
		data.put(name, value);
		return data;
	}

	private List<Statement> createSingleStatement(IRI subject, IRI predicate, Value object)
	{
		return singletonList(valueFactory.createStatement(subject, predicate, object));
	}

	private List<Statement> createMultipleStatements(IRI subject, IRI predicate, List<IRI> objects)
	{
		return objects.stream()
					  .map(object -> valueFactory.createStatement(subject, predicate, object))
					  .collect(toList());
	}
}