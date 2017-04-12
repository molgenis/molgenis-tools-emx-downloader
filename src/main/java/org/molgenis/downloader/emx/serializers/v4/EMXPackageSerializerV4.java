
package org.molgenis.downloader.emx.serializers.v4;

import org.molgenis.downloader.api.EntitySerializer;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;


public class EMXPackageSerializerV4 implements EntitySerializer<Package> {
    private static final String[] FIELDS = {
        "id", "description", "label", "parent", "tags"
    };
            
    @Override
    public List<String> serialize(final Package pkg) {
        final List<String> result = new ArrayList<>();
        result.add(pkg.getId());
        result.add(pkg.getDescription());
        result.add(pkg.getLabel());
        result.add(Optional.ofNullable(pkg.getParent()).map(Package::getFullName).orElse(""));
        result.add(pkg.getTags().stream().map(Tag::getId).collect(joining(",")));
        return result;
    }

    @Override
    public List<String> fields() {
        return Arrays.asList(FIELDS);
    }
    
}
