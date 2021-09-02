package org.molgenis.downloader.rdf;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.eclipse.rdf4j.model.vocabulary.RDF.TYPE;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.molgenis.downloader.api.EntityConsumer;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** {@link EntityConsumer} that creates {@link Statement}s for an entity */
class RdfEntityConsumer implements EntityConsumer {
  static final String IS_ASSOCIATED_WITH_URI = "http://molgenis.org#isAssociatedWith";
  static final DatatypeFactory DATATYPE_FACTORY;
  private static final Logger LOG = LoggerFactory.getLogger(RdfEntityConsumer.class);
  private static final String MAIL_TO = "mailto:";

  static {
    try {
      DATATYPE_FACTORY = DatatypeFactory.newInstance();
    } catch (DatatypeConfigurationException e) {
      throw new InstantiationError("Could not instantiate javax.xml.datatype.DatatypeFactory");
    }
  }

  private final ValueFactory valueFactory;
  private final Consumer<Statement> statements;
  private final Entity entity;
  private final Map<String, Attribute> attributesMap;
  private final RdfConfig rdfConfig;

  RdfEntityConsumer(
      final Entity entity,
      ValueFactory valueFactory,
      Consumer<Statement> statements,
      RdfConfig rdfConfig) {
    this.valueFactory = requireNonNull(valueFactory);
    this.statements = requireNonNull(statements);
    this.entity = requireNonNull(entity);
    this.rdfConfig = requireNonNull(rdfConfig);
    this.attributesMap =
        getAttributes(entity).stream().distinct().collect(toMap(Attribute::getName, a -> a));
  }

  /**
   * Creates an {@link EntityConsumer} for an {@link Entity} and a {@link RepositoryConnection}
   *
   * @param entity {@link Entity} describing the metadata for the data entities we should consume
   * @param connection {@link RepositoryConnection} to get {@link ValueFactory} from and add {@link
   *     Statement}s to.
   * @return newly created {@link EntityConsumer}
   */
  public static EntityConsumer create(
      Entity entity, RepositoryConnection connection, RdfConfig rdfConfig) {
    return new RdfEntityConsumer(entity, connection.getValueFactory(), connection::add, rdfConfig);
  }

  @Override
  public void accept(Map<String, String> data) {
    try {
      IRI subject = createIRI(entity.getFullName(), data.get(getIdAttribute(entity).getName()));
      entity.getTags().stream()
          .filter(tag -> IS_ASSOCIATED_WITH_URI.equals(tag.getRelationIRI()))
          .map(tag -> tryCreateClassStatement(subject, tag))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .forEach(statements);
      data.entrySet().stream()
          .filter(entry -> entry.getValue() != null)
          .forEach(entry -> createStatements(subject, entry.getKey(), entry.getValue()));
    } catch (RuntimeException ex) {
      LOG.warn("Error consuming data, continuing...", ex);
    }
  }

  private IRI createIRI(String entityTypeId, String entityId) {
    return valueFactory.createIRI(
        rdfConfig.getDefaultNamespace(), format("%s/%s", entityTypeId, entityId));
  }

  private Attribute getIdAttribute(Entity entity) {
    return getAttributes(entity).stream()
        .filter(Attribute::isIdAttribute)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Entity " + entity + "has no idAttribute"));
  }

  private Optional<Statement> tryCreateClassStatement(IRI subject, Tag tag) {
    try {
      return Optional.of(
          valueFactory.createStatement(subject, TYPE, valueFactory.createIRI(tag.getObjectIRI())));
    } catch (RuntimeException ex) {
      LOG.error(
          "Error creating class tag for subject {} and object IRI {}",
          subject,
          tag.getObjectIRI(),
          ex);
      return Optional.empty();
    }
  }

  private void createStatements(IRI subject, String attributeName, String attributeValue) {
    Attribute attribute = attributesMap.get(attributeName);
    Set<Tag> attributeTags = attribute.getTags();
    attributeTags.stream()
        .filter(tag -> IS_ASSOCIATED_WITH_URI.equals(tag.getRelationIRI()))
        .forEach(
            tag ->
                tryCreateAttributeValueStatement(
                    subject, attribute, tag.getObjectIRI(), attributeValue));
  }

  private void tryCreateAttributeValueStatement(
      IRI subject, Attribute attribute, String tagObjectIRI, String attributeValue) {
    try {
      createAttributeValueStatement(subject, attribute, tagObjectIRI, attributeValue);
    } catch (RuntimeException ex) {
      LOG.error(
          "Error creating attribute value statement for subject {}, attribute {} and value {}",
          subject,
          attribute.getName(),
          attributeValue,
          ex);
    }
  }

  private void createAttributeValueStatement(
      IRI subject, Attribute attribute, String tagObjectIRI, String attributeValue) {
    IRI predicate = valueFactory.createIRI(tagObjectIRI);
    DataType dataType = attribute.getDataType();
    switch (dataType) {
      case BOOL:
      case DATE:
      case DATE_TIME:
      case DECIMAL:
      case LONG:
      case INT:
      case ENUM:
      case HTML:
      case TEXT:
      case SCRIPT:
      case STRING:
      case EMAIL:
      case HYPERLINK:
      case XREF:
      case CATEGORICAL:
        statements.accept(
            valueFactory.createStatement(
                subject, predicate, createObjectValue(attribute, attributeValue)));
        break;
      case MREF:
      case CATEGORICAL_MREF:
        Arrays.stream(attributeValue.split(","))
            .map(id -> createObjectValue(attribute, id))
            .map(object -> valueFactory.createStatement(subject, predicate, object))
            .forEach(statements);
        break;
      case FILE:
      default:
        throw new IllegalArgumentException("DataType " + dataType + "is not supported");
    }
  }

  private Value createObjectValue(Attribute attribute, String attributeValue) {
    switch (attribute.getDataType()) {
      case BOOL:
        return valueFactory.createLiteral(Boolean.valueOf(attributeValue));
      case DATE:
        return valueFactory.createLiteral(
            DATATYPE_FACTORY.newXMLGregorianCalendar(LocalDate.parse(attributeValue).toString()));
      case DATE_TIME:
        return valueFactory.createLiteral(
            DATATYPE_FACTORY.newXMLGregorianCalendar(Instant.parse(attributeValue).toString()));
      case DECIMAL:
        return valueFactory.createLiteral(Double.valueOf(attributeValue));
      case LONG:
        return valueFactory.createLiteral(Long.valueOf(attributeValue));
      case INT:
        return valueFactory.createLiteral(Integer.valueOf(attributeValue));
      case ENUM:
      case HTML:
      case TEXT:
      case SCRIPT:
      case STRING:
        return valueFactory.createLiteral(attributeValue);
      case EMAIL:
        return valueFactory.createIRI(MAIL_TO + attributeValue);
      case HYPERLINK:
        return valueFactory.createIRI(attributeValue);
      case XREF:
      case CATEGORICAL:
      case MREF:
      case CATEGORICAL_MREF:
        return createIRI(attribute.getRefEntity().getId(), attributeValue);
      default:
        throw new IllegalArgumentException(
            "DataType " + attribute.getDataType() + "is not supported");
    }
  }
}
