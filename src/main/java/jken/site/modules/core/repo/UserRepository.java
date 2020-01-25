/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.846+08:00
 *
 */

package jken.site.modules.core.repo;

import jken.site.modules.core.entity.QUser;
import jken.site.modules.core.entity.User;
import jken.site.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserRepository extends QuerydslEntityRepository<User, Long>, QuerydslBinderCustomizer<QUser> {

    User findByUsernameAndCorpCode(String username, String corpCode);

    @Override
    default void customize(QuerydslBindings querydslBindings, QUser qUser) {
        querydslBindings.bind(qUser.name, qUser.mail, qUser.mobile, qUser.username).first(((path, value) -> path.contains(value)));
        querydslBindings.excluding(qUser.password);
    }
}
