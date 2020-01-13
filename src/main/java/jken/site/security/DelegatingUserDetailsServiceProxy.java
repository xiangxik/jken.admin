package jken.site.security;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DelegatingUserDetailsServiceProxy implements UserDetailsService {

    private volatile UserDetailsService target;

    private ApplicationContext applicationContext;

    private Class<?> userDetailsServiceInterface = UserDetailsService.class;

    public DelegatingUserDetailsServiceProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public DelegatingUserDetailsServiceProxy(ApplicationContext applicationContext, Class<?> userDetailsServiceInterface) {
        this(applicationContext);
        this.userDetailsServiceInterface = userDetailsServiceInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (target == null) {
            synchronized (this) {
                if (target == null) {
                    target = (UserDetailsService) this.applicationContext.getBean(userDetailsServiceInterface);
                    if (target == null) {
                        throw new RuntimeException("userDetailsService not found.");
                    }
                }
            }
        }
        return target.loadUserByUsername(username);
    }
}
