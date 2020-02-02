/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-02T21:11:00.877+08:00
 */

package jken.security;

import jken.integration.Authority;
import jken.integration.DomainAuthorities;
import jken.integration.IntegrationServiceLoader;
import jken.integration.ModuleMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity.IgnoredRequestConfigurer ignore = web.ignoring().antMatchers("/static/**", "/favicon.ico", "/h2/**");
        List<ModuleMetadata> moduleMetadatas = IntegrationServiceLoader.getModuleMetadata();
        for (ModuleMetadata metadata : moduleMetadatas) {
            if (metadata.getIgnorePatterns() != null && metadata.getIgnorePatterns().length > 0) {
                ignore.antMatchers(metadata.getIgnorePatterns());
            }
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable().and().formLogin().loginPage("/login").successHandler(authenticationSuccessHandler()).permitAll().and().rememberMe().and().logout().permitAll();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
//        urlRegistry.withObjectPostProcessor(new ObjectPostProcessor<Object>() {
//            public <T> T postProcess(T object) {
//                if (object instanceof FilterSecurityInterceptor) {
//                    ((FilterSecurityInterceptor) object).setRunAsManager(new RunAsManagerImpl());
//                }
//                return object;
//            }
//        });

        List<ModuleMetadata> moduleMetadatas = IntegrationServiceLoader.getModuleMetadata();
        for (ModuleMetadata metadata : moduleMetadatas) {
            for (DomainAuthorities domainAuthorities : metadata.getDomainAuthoritiesCollection()) {
                for (Authority authority : domainAuthorities.getAuthorities()) {
                    switch (authority.getPatternType()) {
                        case REGEX:
                            registerUrlAuthorization(authority, urlRegistry::regexMatchers, urlRegistry::regexMatchers);
                            break;
                        case MVC:
                            registerUrlAuthorization(authority, urlRegistry::mvcMatchers, urlRegistry::mvcMatchers);
                            break;
                        case ANT:
                        default:
                            registerUrlAuthorization(authority, urlRegistry::antMatchers, urlRegistry::antMatchers);
                            break;
                    }
                }
            }
        }
        urlRegistry.anyRequest().authenticated();
    }

    private void registerUrlAuthorization(Authority authority, Function<String[], ExpressionUrlAuthorizationConfigurer.AuthorizedUrl> function, BiFunction<HttpMethod, String[], ExpressionUrlAuthorizationConfigurer.AuthorizedUrl> function2) {
        if (authority.getHttpMethods() == null || authority.getHttpMethods().length == 0) {
            function.apply(authority.getPatterns()).hasAuthority(authority.getCode());
        } else {
            for (HttpMethod httpMethod : authority.getHttpMethods()) {
                function2.apply(httpMethod, authority.getPatterns()).hasAuthority(authority.getCode());
            }
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new DelegatingUserDetailsServiceProxy(getApplicationContext(), AbstractUserDetailsService.class)).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CorpCodeSavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
