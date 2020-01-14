/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-21T21:19:36.172+08:00
 *
 */

package jken.site;

import jken.site.support.data.jpa.DataRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableJpaRepositories(basePackages = "jken.site", repositoryFactoryBeanClass = DataRepositoryFactoryBean.class)
@EnableJpaAuditing
public class JkenSiteApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JkenSiteApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JkenSiteApplication.class);
    }
}
