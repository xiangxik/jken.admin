/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:02:44.048+08:00
 */

package jken.module.cms.controller;

import com.querydsl.core.types.Predicate;
import jken.module.cms.entity.Article;
import jken.support.web.CrudController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tag")
public class ArticleController extends CrudController<Article, Long> {
    @Override
    public Page<Article> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
