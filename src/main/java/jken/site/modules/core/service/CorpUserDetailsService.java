/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.849+08:00
 *
 */

package jken.site.modules.core.service;

import jken.site.modules.core.entity.User;
import jken.site.modules.core.repo.UserRepository;
import jken.site.security.AbstractUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorpUserDetailsService extends AbstractUserDetailsService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected User loadRepoUserDetails(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    protected User loadUserByUsernameAndCorpCode(String username, String corpCode) {
        return userRepository.findByUsernameAndCorpCode(username, corpCode);
    }
}
