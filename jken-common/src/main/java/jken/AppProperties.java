/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-08T12:28:42.204+08:00
 */

package jken;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app")
public class AppProperties {

    private String name = "jken";
    private String version = "1.0";

    private final Security security = new Security();

    public static class Security {

        private List<String> ignorePatterns = Lists.newArrayList("/static/**", "/favicon.ico", "/h2/**");

        public List<String> getIgnorePatterns() {
            return ignorePatterns;
        }

        public void setIgnorePatterns(List<String> ignorePatterns) {
            this.ignorePatterns = ignorePatterns;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Security getSecurity() {
        return security;
    }
}
