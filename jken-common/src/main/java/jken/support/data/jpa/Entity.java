/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.420+08:00
 */

package jken.support.data.jpa;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface Entity<I extends Serializable> extends Persistable<I> {
}
