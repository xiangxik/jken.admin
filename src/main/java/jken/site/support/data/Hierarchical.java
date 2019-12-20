/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.882+08:00
 *
 */

package jken.site.support.data;

import java.util.List;

public interface Hierarchical<T extends Hierarchical<T>> extends Sortable {
    T getParent();

    void setParent(T parent);

    List<T> getChildren();

    void setChildren(List<T> children);
}
