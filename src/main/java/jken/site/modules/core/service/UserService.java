/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.850+08:00
 *
 */

package jken.site.modules.core.service;

import jken.site.modules.core.entity.User;
import jken.site.support.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User, Long> {
}
