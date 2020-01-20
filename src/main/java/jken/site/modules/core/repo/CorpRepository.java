/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.845+08:00
 *
 */

package jken.site.modules.core.repo;

import jken.site.modules.core.entity.Corp;
import jken.site.modules.core.entity.QCorp;
import jken.site.support.data.jpa.EntityRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CorpRepository extends EntityRepository<Corp, Long>, QuerydslPredicateExecutor<Corp>, QuerydslBinderCustomizer<QCorp> {
    @Override
    default void customize(QuerydslBindings querydslBindings, QCorp qCorp) {
        querydslBindings.bind(qCorp.code).first(((path, value) -> path.startsWith(value)));
    }
}
