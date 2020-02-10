/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-10T21:03:20.238+08:00
 */

package jken.module.cms.controller;

import com.querydsl.core.types.Predicate;
import jken.module.cms.entity.ArticleCategory;
import jken.support.web.CrudController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/articleCategory")
public class ArticleCategoryController extends CrudController<ArticleCategory, Long> {
    @Override
    public Page<ArticleCategory> list(Predicate predicate, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping(produces = "application/json", params = "tree")
    @ResponseBody
    public Page<Map<String, Object>> tree(@QuerydslPredicate(root = ArticleCategory.class) Predicate predicate, Pageable pageable) {
        Page<ArticleCategory> page = super.doInternalPage(predicate, pageable);
        return page.map(this::convertToTreeData);
    }

    private Map<String, Object> convertToTreeData(ArticleCategory mi) {
        Map<String, Object> data = new HashMap<>();

        return data;
    }
}
