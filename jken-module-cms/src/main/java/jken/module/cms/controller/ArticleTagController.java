/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:01:59.859+08:00
 */

package jken.module.cms.controller;

import com.querydsl.core.types.Predicate;
import jken.module.cms.entity.ArticleTag;
import jken.support.web.CrudController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articleTag")
public class ArticleTagController extends CrudController<ArticleTag, Long> {
    @Override
    public Page<ArticleTag> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
