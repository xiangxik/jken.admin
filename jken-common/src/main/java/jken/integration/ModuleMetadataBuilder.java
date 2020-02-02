/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-02T21:11:00.876+08:00
 */

package jken.integration;

import org.apache.commons.lang3.builder.Builder;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleMetadataBuilder implements Builder<ModuleMetadata> {

    private String moduleName;

    private String[] ignorePatterns;
    private List<DomainAuthoritiesRegistry> domainAuthoritiesRegistries = new ArrayList<>();

    public ModuleMetadataBuilder(String moduleName) {
        this.moduleName = moduleName;
    }

    public DomainAuthoritiesRegistry domain(String domainName, String domainCode) {
        DomainAuthorities domainAuthorities = new DomainAuthorities(domainName, domainCode);
        DomainAuthoritiesRegistry domainAuthoritiesRegistry = new DomainAuthoritiesRegistry(this, domainAuthorities);
        domainAuthoritiesRegistries.add(domainAuthoritiesRegistry);
        return domainAuthoritiesRegistry;
    }

    public ModuleMetadataBuilder ignore(String... patterns) {
        this.ignorePatterns = patterns;
        return this;
    }

    public DomainAuthoritiesRegistry domainCrud(String domainName, String domainCode, String pattern) {
        return domain(domainName, "corp")
                .addAuthority(domainName + "列表", domainCode + "-list", new String[]{pattern}, Authority.PatternType.ANT, HttpMethod.GET)
                .addAuthority(domainName + "查看", domainCode + "-view", new String[]{pattern + "/*"}, Authority.PatternType.ANT, HttpMethod.GET)
                .addAuthority(domainName + "新增", domainCode + "-add", new String[]{pattern + "/*"}, Authority.PatternType.ANT, HttpMethod.POST)
                .addAuthority(domainName + "修改", domainCode + "-edit", new String[]{pattern + "/*"}, Authority.PatternType.ANT, HttpMethod.PUT)
                .addAuthority(domainName + "删除", domainCode + "-delete", new String[]{pattern}, Authority.PatternType.ANT, HttpMethod.DELETE);

    }

    @Override
    public ModuleMetadata build() {
        ModuleMetadata moduleMetadata = new ModuleMetadata();
        moduleMetadata.setName(moduleName);
        moduleMetadata.setIgnorePatterns(ignorePatterns);
        moduleMetadata.setDomainAuthoritiesCollection(domainAuthoritiesRegistries.stream().map(registry -> registry.get()).collect(Collectors.toList()));
        return moduleMetadata;
    }

    public static class DomainAuthoritiesRegistry {

        private ModuleMetadataBuilder metadataBuilder;
        private DomainAuthorities domainAuthorities;

        public DomainAuthoritiesRegistry(ModuleMetadataBuilder metadataBuilder, DomainAuthorities domainAuthorities) {
            this.metadataBuilder = metadataBuilder;
            this.domainAuthorities = domainAuthorities;
        }

        public DomainAuthoritiesRegistry addAuthority(String name, String code, String... patterns) {
            return addAuthority(name, code, patterns, null, null);
        }

        public DomainAuthoritiesRegistry addAuthority(String name, String code, String[] patterns, Authority.PatternType patternType, HttpMethod... httpMethods) {
            Authority authority = new Authority();
            authority.setName(name);
            authority.setCode(code);
            authority.setPatterns(patterns);
            authority.setPatternType(patternType);
            authority.setHttpMethods(httpMethods);
            domainAuthorities.getAuthorities().add(authority);
            return this;
        }

        public ModuleMetadataBuilder and() {
            return metadataBuilder;
        }

        public DomainAuthorities get() {
            return domainAuthorities;
        }

    }

}
