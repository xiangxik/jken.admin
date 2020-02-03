/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.750+08:00
 */

package jken.integration;

import com.google.common.collect.Lists;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.*;

public class IntegrationService {

    final private static List<ModuleMetadata> moduleMetadatas;
    final private static Set<Authority> authorities;

    static {
        moduleMetadatas = new ArrayList<>();
        authorities = new HashSet<>();
        ServiceLoader<ModuleIntegration> integrations = ServiceLoader.load(ModuleIntegration.class);
        List<ModuleIntegration> list = Lists.newArrayList(integrations);
        AnnotationAwareOrderComparator.sort(list);
        for (ModuleIntegration integration : list) {
            ModuleMetadata metadata = new ModuleMetadata();
            integration.integrate(metadata);

            for (DomainAuthorities domainAuthorities : metadata.getDomainAuthoritiesCollection()) {
                for (Authority authority : domainAuthorities.getAuthorities()) {
                    authorities.add(authority);
                }
            }
            moduleMetadatas.add(metadata);
        }
    }

    public static List<ModuleMetadata> getModuleMetadata() {
        return moduleMetadatas;
    }

    public static Set<Authority> getAuthorities() {
        return authorities;
    }
}
