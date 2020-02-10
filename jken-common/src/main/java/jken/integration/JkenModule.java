/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-09T21:20:25.643+08:00
 */

package jken.integration;

import java.util.ArrayList;
import java.util.List;

public class JkenModule {

    private String name;
    private String[] ignorePatterns;

    private final List<Domain> domains = new ArrayList<>();
    private final List<Mi> menus = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIgnorePatterns() {
        return ignorePatterns;
    }

    public void setIgnorePatterns(String[] ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public List<Mi> getMenus() {
        return menus;
    }

    public static class Domain {
        private String name;
        private String code;
        private boolean crud = false;

        private final List<Authority> authorities = new ArrayList<>();

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

        public boolean isCrud() {
            return crud;
        }

        public void setCrud(boolean crud) {
            this.crud = crud;
        }

        public List<Authority> getAuthorities() {
            return authorities;
        }
    }

    public static class Mi {
        private String name;
        private String code;
        private String href;
        private String iconCls;
        private String authorities;

        private final List<Mi> children = new ArrayList<>();

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

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getIconCls() {
            return iconCls;
        }

        public void setIconCls(String iconCls) {
            this.iconCls = iconCls;
        }

        public String getAuthorities() {
            return authorities;
        }

        public void setAuthorities(String authorities) {
            this.authorities = authorities;
        }

        public List<Mi> getChildren() {
            return children;
        }
    }
}
