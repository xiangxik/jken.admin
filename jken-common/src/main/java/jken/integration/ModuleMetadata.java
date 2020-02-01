/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T21:44:55.233+08:00
 */

package jken.integration;

import java.util.List;

public class ModuleMetadata {

    private String name;

    private List<DomainAuthorities> domainAuthoritiesCollection;

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
}
