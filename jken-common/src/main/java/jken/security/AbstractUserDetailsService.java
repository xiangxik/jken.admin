/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.403+08:00
 */

package jken.security;

import com.google.common.base.Strings;
import jken.support.data.Corpable;
import jken.support.data.jpa.Entity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractUserDetailsService<U extends UserDetails, I extends Serializable> implements UserDetailsService, AuditorAware<U> {

    protected abstract U loadRepoUserDetails(I id);

    protected abstract U loadUserByUsernameAndCorpCode(String username, String corpCode);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String corpCode = obtainCorpCode();

        U user = loadUserByUsernameAndCorpCode(username, corpCode);

        if (user == null) {
            throw new UsernameNotFoundException("Not found user " + username);
        }

        I id = null;
        if (user instanceof Entity) {
            id = ((Entity<I>) user).getId();
        }
        return new CustomUserDetails<>(user instanceof Corpable ? ((Corpable) user).getCorpCode() : null, id, user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                user.getAuthorities() == null ? AuthorityUtils.createAuthorityList("ROLE_USER") : user.getAuthorities());
    }

    @Override
    public Optional<U> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return Optional.of(loadRepoUserDetails(((CustomUserDetails<I>) principal).getId()));
        }
        return Optional.empty();
    }

    protected String obtainCorpCode() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String corpCode = null;
        if (requestAttributes != null) {
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                corpCode = request.getParameter(CustomUserDetails.CORP_CODE_REQUEST_PARAMETER);

                if (Strings.isNullOrEmpty(corpCode)) {
                    Cookie cookie = WebUtils.getCookie(request, CustomUserDetails.CORP_CODE_COOKIE);
                    if (cookie != null) {
                        corpCode = cookie.getValue();
                    }
                }
            }
        }

        return corpCode;
    }
}
