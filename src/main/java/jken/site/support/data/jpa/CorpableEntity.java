/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.862+08:00
 *
 */

package jken.site.support.data.jpa;

import jken.site.support.data.Corpable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class CorpableEntity<U, I extends Serializable> extends DataEntity<U, I> implements Corpable {

    @Column(length = 100)
    private String corpCode;

    @Override
    public String getCorpCode() {
        return corpCode;
    }

    @Override
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
