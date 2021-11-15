package org.molgenis.downloader.emx.serializers.v9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.Tag;

public class EMXTagSerializerV92 implements EntitySerializer<Tag> {
  private static final String[] FIELDS = {
    "identifier", "label", "relationLabel", "objectIRI", "relationIRI", "value", "codeSystem"
  };

  @SuppressWarnings("DuplicatedCode")
  @Override
  public List<String> serialize(final Tag tag) {
    final List<String> fields = new ArrayList<>();
    fields.add(tag.getId());
    fields.add(tag.getLabel());
    fields.add(tag.getRelationLabel());
    fields.add(Optional.ofNullable(tag.getObjectIRI()).orElse(""));
    fields.add(Optional.ofNullable(tag.getRelationIRI()).orElse(""));
    fields.add(Optional.ofNullable(tag.getValue()).orElse(""));
    fields.add(tag.getCodeSystem());
    return fields;
  }

  @Override
  public List<String> fields() {
    return Arrays.asList(FIELDS);
  }
}
