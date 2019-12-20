/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.846+08:00
 *
 */

package jken.site.modules.core.repo;

import jken.site.modules.core.entity.User;
import jken.site.support.data.jpa.EntityRepository;

public interface UserRepository extends EntityRepository<User, Long> {

    User findByUsernameAndCorpCode(String username, String corpCode);

}
