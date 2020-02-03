/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.752+08:00
 */

package jken.integration;

import org.springframework.http.HttpMethod;

public class ModuleMetadataDecorator {

    private ModuleMetadata metadata;

    public ModuleMetadataDecorator(ModuleMetadata metadata) {
        this.metadata = metadata;
    }

    public ModuleMetadataDecorator name(String name) {
        this.metadata.setName(name);
        return this;
    }

    public DomainAuthoritiesRegistry domain(String domainName, String domainCode) {
        DomainAuthorities domainAuthorities = new DomainAuthorities(domainName, domainCode);
        this.metadata.getDomainAuthoritiesCollection().add(domainAuthorities);

        return new DomainAuthoritiesRegistry(this, domainAuthorities);
    }

    public ModuleMetadataDecorator ignore(String... patterns) {
        this.metadata.setIgnorePatterns(patterns);
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

    public static class DomainAuthoritiesRegistry {

        private ModuleMetadataDecorator metadataDecorator;
        private DomainAuthorities domainAuthorities;

        public DomainAuthoritiesRegistry(ModuleMetadataDecorator metadataDecorator, DomainAuthorities domainAuthorities) {
            this.metadataDecorator = metadataDecorator;
            this.domainAuthorities = domainAuthorities;
        }

        public DomainAuthoritiesRegistry addAuthority(String name, String code, String... patterns) {
            return addAuthority(name, code, patterns, Authority.PatternType.ANT, (HttpMethod[]) null);
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

        public ModuleMetadataDecorator and() {
            return metadataDecorator;
        }

    }

}
