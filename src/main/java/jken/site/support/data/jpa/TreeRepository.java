/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.876+08:00
 *
 */

package jken.site.support.data.jpa;

import java.io.Serializable;
import java.util.List;

public interface TreeRepository<T extends TreeEntity<T, U, I>, U, I extends Serializable> extends EntityRepository<T, I> {

    List<T> findRoots();

    List<T> findAllChildren(T root);

}
