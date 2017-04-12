
package org.molgenis.downloader.emx.serializers.v4;

import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.*;
import org.molgenis.downloader.emx.serializers.v3.EMXAttributeSerializerV3;

import java.util.*;

import static java.util.stream.Collectors.joining;


public class EMXAttributeSerializerV4 extends EMXAttributeSerializerV3 implements EntitySerializer<Attribute> {

    public EMXAttributeSerializerV4(Collection<Language> languages) {
        super(languages);
    }
}
