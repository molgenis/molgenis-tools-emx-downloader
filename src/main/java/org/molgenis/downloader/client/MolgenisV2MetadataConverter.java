
package org.molgenis.downloader.client;

import java.net.URI;
import java.util.Map;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Backend;
import org.molgenis.downloader.api.metadata.DataType;
import org.molgenis.downloader.api.metadata.Entity;
import org.molgenis.downloader.api.metadata.Language;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;


class MolgenisV2MetadataConverter extends AbstractMetadataConverter {

    private final MetadataRepository repository;

    public MolgenisV2MetadataConverter(MetadataRepository metadataRepository) {
        repository = metadataRepository;
    }

    @Override
    public Attribute toAttribute(Map<Attribute, String> data) {
        final Attribute att = repository.createAttribute(getString(data, "id"));
        setString(data, "name", att::setName);
        setData(data, "type", DataType::from, att::setDataType);
        setData(data, "entity", repository::createEntity, att::setEntity);
        setBoolean(data, "isIdAttribute", att::setIdAttribute);
        setBoolean(data, "isLabelAttribute", att::setLabelAttribute);
        setInteger(data, "lookupAttributeIndex", i -> att.setLookupAttribute(true));
        setData(data, "parent", repository::createAttribute, att::setCompound);
        setList(data, "children", repository::createAttribute, part -> {
                part.setCompound(att);
                att.addPart(part);
            });
        setData(data, "refEntityType", repository::createEntity, att::setRefEntity);
        setData(data, "mappedBy", repository::createAttribute, att::setMappedBy);
        setString(data, "orderBy", att::setOrderBy);
        setString(data, "expression", att::setExpression);
        setBoolean(data, "isNullable", att::setOptional);
        setBoolean(data, "isVisible", att::setVisible);
        setString(data, "label", att::setLabel);
        setString(data, "description", att::setDescription);
        setBoolean(data, "isAggregatable", att::setAggregateable);
        setString(data, "enumOptions", att::setEnumOptions);
        setInteger(data, "rangeMin", att::setRangeMin);
        setInteger(data, "rangeMax", att::setRangeMax);
        setBoolean(data, "isReadOnly", att::setReadOnly);
        setBoolean(data, "isUnique", att::setUnique);
        setBoolean(data, "isAuto", att::setAuto);
        setList(data, "tags", repository::createTag, att::addTag);
        setString(data, "visibleExpression", att::setVisibleExpression);
        setString(data, "validationExpression", att::setValidationExpression);
        setString(data, "defaultValue", att::setDefaultValue);

        // IGNORED: sequenceNr
        return att;
    }

    @Override
    public Entity toEntity(Map<Attribute, String> data) {
        final Entity ent = repository.createEntity(getString(data, "fullName"));
        setData(data, "backend", Backend::from, ent::setBackend);
        setData(data, "package", repository::createPkg, ent::setPkg);
        setBoolean(data, "isAbstract", ent::setAbstractClass);
        setString(data, "label", ent::setLabel);
        setData(data, "extends", repository::createEntity, ent::setBase);
        setString(data, "description", ent::setDescription);
        setList(data, "tags", repository::createTag, ent::addTag);
        setList(data, "attributes", repository::createAttribute, att -> {
            ent.addAttribute(att);
            updateReference(ent, att);
        });
        repository.getLanguages().forEach(lang -> setString(data, "description-" + lang.getCode(), description -> ent.addDescription(description, lang)));
        repository.getLanguages().forEach(lang -> setString(data, "label-" + lang.getCode(), label -> ent.addLabel(label, lang)));
        return ent;
    }

    @Override
    public Package toPackage(Map<Attribute, String> data) {
        final Package pkg = repository.createPkg(getString(data, "fullName"));
        setString(data, "label", pkg::setLabel);
        setString(data, "description", pkg::setDescription);
        setData(data, "parent", repository::createPkg, pkg::setParent);
        setList(data, "tags", repository::createTag, pkg::addTag);
        return pkg;
    }

    @Override
    public Tag toTag(Map<Attribute, String> data) {
        final Tag tag = repository.createTag(getString(data, "id"));
        setString(data, "label", tag::setLabel);
        setData(data, "objectIRI", URI::create, tag::setObjectIRI);
        setData(data, "relationIRI", URI::create, tag::setRelationIRI);
        setString(data, "relationLabel", tag::setRelationLabel);
        setString(data, "codeSystem", tag::setCodeSystem);
        return tag;
    }

    @Override
    public Language toLanguage(Map<Attribute, String> data) {
        final Language lng = repository.createLanguage(getString(data, "code"));
        setString(data, "name", lng::setName);
        setBoolean(data, "active", lng::setActive);
        return lng;
    }

    @Override
    public String getTagsRepository() {
        return "sys_md_Tag";
    }

    @Override
    public String getPackagesRepository() {
        return "sys_md_Package";
    }

    @Override
    public String getEnitiesRepository() {
        return "sys_md_EntityType";
    }

    @Override
    public String getAttributesRepository() {
        return "sys_md_Attribute";
    }

    @Override
    public String getLanguagesRepository() {
        return "sys_Language";
    }
}
