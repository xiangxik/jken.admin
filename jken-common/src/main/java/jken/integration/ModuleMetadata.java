/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-02T21:11:00.875+08:00
 */

package jken.integration;

import java.util.ArrayList;
import java.util.List;

public class ModuleMetadata {

    private String name;

    private List<DomainAuthorities> domainAuthoritiesCollection = new ArrayList<>();
    private String[] ignorePatterns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DomainAuthorities> getDomainAuthoritiesCollection() {
        return domainAuthoritiesCollection;
    }

    public void setDomainAuthoritiesCollection(List<DomainAuthorities> domainAuthoritiesCollection) {
        this.domainAuthoritiesCollection = domainAuthoritiesCollection;
    }

    public String[] getIgnorePatterns() {
        return ignorePatterns;
    }

    public void setIgnorePatterns(String[] ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
    }
}
