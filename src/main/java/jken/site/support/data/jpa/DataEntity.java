/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.864+08:00
 *
 */

package jken.site.support.data.jpa;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class DataEntity<U, I extends Serializable> extends AbstractAuditable<U, I> implements Entity<I> {

}