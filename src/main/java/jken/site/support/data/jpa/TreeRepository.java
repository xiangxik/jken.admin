/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.876+08:00
 *
 */

package jken.site.support.data.jpa;

import jken.site.support.data.Tree;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<T, ?, I>, I extends Serializable> extends EntityRepository<T, I> {

    List<T> findRoots();

    List<T> findAllChildren(T root);

    Tree<T> findByRoot(T root);

}
