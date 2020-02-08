/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.647+08:00
 */

package jken.security;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import jken.AppProperties;
import jken.integration.Authority;
import jken.support.data.jpa.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.AuditorAware;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractUserDetailsService<U extends UserDetails, I extends Serializable> implements UserDetailsService, AuditorAware<U>, ApplicationContextAware {

    @Autowired
    private AppProperties properties;

    private ApplicationContext applicationContext;

    protected abstract U loadRepoUserDetails(I id);

    protected abstract U loadUserByUsernameAndCorpCode(String username, String corpCode);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerHolder entityManagerHolder = new EntityManagerHolder(entityManager);
        TransactionSynchronizationManager.bindResource(entityManagerFactory, entityManagerHolder);

        String corpCode = obtainCorpCode();

        U user = loadUserByUsernameAndCorpCode(username, corpCode);

        if (user == null) {
            throw new UsernameNotFoundException("Not found user " + username);
        }

        I id = null;
        if (user instanceof Entity) {
            id = ((Entity<I>) user).getId();
        }
        Collection<? extends GrantedAuthority> authorityCollection = user.getAuthorities();
        List<GrantedAuthority> authorities = Lists.newArrayList(authorityCollection);

        TransactionSynchronizationManager.unbindResource(entityManagerFactory);
        EntityManagerFactoryUtils.closeEntityManager(entityManager);

        boolean isOwnerCorp = Objects.equals(properties.getOwnerCorp(), corpCode);
        if (isOwnerCorp) {
            if (authorities.stream().anyMatch(authority -> Objects.equals(authority.getAuthority(), Authority.ROLE_ADMIN))) {
                authorities.add(Authority.SUPER_ADMIN);
            }
        }

        return new CustomUserDetails<>(corpCode, isOwnerCorp, id, user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                authorities);
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
