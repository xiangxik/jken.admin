/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.841+08:00
 *
 */

package jken.site.modules.core.entity;

import jken.site.support.data.jpa.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_corp")
public class Corp extends DataEntity<User, Long> {

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(unique = true, length = 100)
    private String code;

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
}
