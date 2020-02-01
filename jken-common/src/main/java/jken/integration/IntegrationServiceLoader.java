/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T21:44:55.231+08:00
 */

package jken.integration;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class IntegrationServiceLoader {

    public static List<ModuleMetadata> getModuleMetadata() {
        ServiceLoader<ModuleIntegration> integrations = ServiceLoader.load(ModuleIntegration.class);
        return integrations.stream().map(IntegrationServiceLoader::convert).collect(Collectors.toList());
    }

    private static ModuleMetadata convert(ServiceLoader.Provider<ModuleIntegration> integrationProvider) {
        return integrationProvider.get().integrate();
    }
}
