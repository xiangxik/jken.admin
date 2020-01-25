/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.841+08:00
 *
 */

package jken.site.modules.core.entity;

import jken.site.support.data.Disabledable;
import jken.site.support.data.jpa.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_corp")
public class Corp extends DataEntity<User, Long> implements Disabledable {

    @NotNull
    @Column(length = 100, nullable = false)
    private String name;
    @NotNull
    @Column(unique = true, length = 100, nullable = false)
    private String code;
    @Column(length = 20)
    private Status status;
    @Column(length = 200)
    private String logo;
    @Column(length = 200)
    private String website;
    @Column(length = 1000)
    private String introduction;
    private boolean disabled = false;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public enum Status {
        NORMAL,
        ARREARAGE
    }
}
