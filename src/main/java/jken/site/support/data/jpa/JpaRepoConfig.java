/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.872+08:00
 *
 */

package jken.site.support.data.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "jken.site", repositoryFactoryBeanClass = DataRepositoryFactoryBean.class)
@EnableJpaAuditing
public class JpaRepoConfig {

}
