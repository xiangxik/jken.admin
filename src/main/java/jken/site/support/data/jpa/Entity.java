/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.866+08:00
 *
 */

package jken.site.support.data.jpa;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface Entity<I extends Serializable> extends Persistable<I> {
}
