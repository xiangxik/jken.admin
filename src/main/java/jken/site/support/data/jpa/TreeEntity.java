/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.875+08:00
 *
 */

package jken.site.support.data.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jken.site.support.data.Hierarchical;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class TreeEntity<T extends TreeEntity<T, U, I>, U, I extends Serializable> extends SortEntity<U, I> implements Hierarchical<T> {

    @Column(length = 500)
    private String treePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private T parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @OrderBy("sortNo asc")
    @JsonManagedReference
    private List<T> children;

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public void setParent(T parent) {
        this.parent = parent;
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<T> children) {
        this.children = children;
    }
}
