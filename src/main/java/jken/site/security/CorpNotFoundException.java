/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.855+08:00
 *
 */

package jken.site.security;

import org.springframework.security.core.AuthenticationException;

public class CorpNotFoundException extends AuthenticationException {

    private static final long serialVersionUID = -5571907595893230781L;

    public CorpNotFoundException(String msg) {
        super(msg);
    }

    public CorpNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

}
