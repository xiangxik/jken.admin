/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.410+08:00
 */

package jken.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class CustomUserDetails<I extends Serializable> extends User {

    public static final String CORP_CODE_REQUEST_PARAMETER = "corpCode";
    public static final String CORP_CODE_COOKIE = "corp_code";
    private static final long serialVersionUID = 8063484673226426535L;
    private final I id;

    private final String corpCode;

    private final boolean ownerCorp;

    public CustomUserDetails(String corpCode, boolean ownerCorp, I id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.id = id;
        this.corpCode = corpCode;
        this.ownerCorp = ownerCorp;
    }

    public I getId() {
        return id;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public boolean isOwnerCorp() {
        return ownerCorp;
    }
}
