/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-21T21:19:36.168+08:00
 *
 */

package jken.site.support.data.jpa;

import jken.site.support.data.Sortable;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class SortEntity<U, I extends Serializable> extends DataEntity<U, I> implements Sortable, Comparable<SortEntity<U, I>> {
    private Integer sortNo;

    @Override
    public Integer getSortNo() {
        return sortNo;
    }

    @Override
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public int compareTo(SortEntity<U, I> o) {
        return new CompareToBuilder().append(getSortNo(), o.getSortNo()).append(getId(), o.getId()).toComparison();
    }
}
