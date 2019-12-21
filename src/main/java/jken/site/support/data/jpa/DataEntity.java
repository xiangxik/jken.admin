/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-21T21:19:36.162+08:00
 *
 */

package jken.site.support.data.jpa;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class DataEntity<U, I extends Serializable> extends AbstractAuditable<U, I> implements Entity<I> {

}