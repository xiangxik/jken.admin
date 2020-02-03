/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.747+08:00
 */

package jken.integration;

import org.springframework.http.HttpMethod;

import java.util.Objects;

public class Authority {

    private String name;
    private String code;
    private String[] patterns;
    private PatternType patternType = PatternType.ANT;
    private HttpMethod[] httpMethods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public PatternType getPatternType() {
        return patternType;
    }

    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }

    public HttpMethod[] getHttpMethods() {
        return httpMethods;
    }

    public void setHttpMethods(HttpMethod[] httpMethods) {
        this.httpMethods = httpMethods;
    }

    public enum PatternType {
        ANT, REGEX, MVC
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return code.equals(authority.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
