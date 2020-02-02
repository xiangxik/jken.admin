/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-02T21:11:00.874+08:00
 */

package jken.integration;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class IntegrationServiceLoader {

    private static volatile List<ModuleMetadata> moduleMetadatas;

    public static List<ModuleMetadata> getModuleMetadata() {
        if (moduleMetadatas == null) {
            synchronized (IntegrationServiceLoader.class) {
                if (moduleMetadatas == null) {
                    ServiceLoader<ModuleIntegration> integrations = ServiceLoader.load(ModuleIntegration.class);
                    moduleMetadatas = integrations.stream().map(IntegrationServiceLoader::convert).collect(Collectors.toList());
                }
            }
        }
        return moduleMetadatas;
    }

    private static ModuleMetadata convert(ServiceLoader.Provider<ModuleIntegration> integrationProvider) {
        return integrationProvider.get().integrate();
    }
}
