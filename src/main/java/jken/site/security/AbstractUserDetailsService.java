/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.851+08:00
 *
 */

package jken.site.security;

import com.google.common.base.Strings;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
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

        return loadUserByUsernameAndCorpCode(username, corpCode);
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
