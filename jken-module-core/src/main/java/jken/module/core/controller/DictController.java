/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T18:21:53.198+08:00
 */

package jken.module.core.controller;

import com.querydsl.core.types.Predicate;
import jken.module.core.entity.Dict;
import jken.module.core.service.DictService;
import jken.support.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dict")
public class DictController extends CrudController<Dict, Long> {

    @Autowired
    private DictService dictService;

    @Override
    public Page<Dict> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    @Override
    protected void onSave(Dict entity) {
        dictService.saveWithItems(entity, entity.getItems());
    }
}
