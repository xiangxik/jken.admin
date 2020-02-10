/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:00:11.077+08:00
 */

package jken.module.cms.service;

import jken.module.cms.entity.Article;
import jken.support.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends CrudService<Article, Long> {
}
