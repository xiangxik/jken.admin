/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.486+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.User;
import jken.module.core.repo.UserRepository;
import jken.security.AbstractUserDetailsService;
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
