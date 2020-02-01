/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.470+08:00
 */

package jken.module.core.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import jken.module.core.entity.MenuItem;
import jken.module.core.service.MenuItemService;
import jken.support.data.TreeHelper;
import jken.support.web.CrudController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/menu")
public class MenuController extends CrudController<MenuItem, Long> {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping(produces = "application/json", params = "tree")
    @ResponseBody
    public Page<Map<String, Object>> tree(@QuerydslPredicate(root = MenuItem.class) Predicate predicate, Pageable pageable) {
        Page<MenuItem> page = super.doInternalPage(predicate, pageable);
        return page.map(this::convertToTreeData);
    }

    @Override
    public Page<MenuItem> list(Predicate predicate, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping(value = "/select", produces = "application/json")
    @ResponseBody
    public Iterable<Map<String, Object>> list(@RequestParam(value = "selected[]", required = false) MenuItem[] selected, @RequestParam(value = "disabled[]", required = false) MenuItem[] disabled, @QuerydslPredicate(root = MenuItem.class) Predicate predicate) {
        Iterable<MenuItem> roots = menuItemService.findAll(predicate);
        List<MenuItem> entities = TreeHelper.toTree(Lists.newArrayList(roots));

        return convertToSelect(entities, selected, disabled);
    }

    private Iterable<Map<String, Object>> convertToSelect(List<MenuItem> entities, MenuItem[] selected, MenuItem[] disabled) {
        if (entities == null || entities.size() == 0) {
            return null;
        }
        return entities.stream().map(entity -> {
            Map<String, Object> data = new HashMap<>();
            data.put("value", entity.getId());
            data.put("name", entity.getName());
            if (selected != null && ArrayUtils.contains(selected, entity)) {
                data.put("selected", true);
            }
            if (disabled != null && ArrayUtils.contains(disabled, entity)) {
                data.put("disabled", true);
            }
            data.put("children", convertToSelect(entity.getChildren(), selected, disabled));
            return data;
        }).collect(Collectors.toList());
    }

    private Map<String, Object> convertToTreeData(MenuItem mi) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", mi.getId());
        data.put("pid", mi.getParent() == null ? 0l : mi.getParent().getId());
        data.put("name", mi.getName());
        data.put("href", StringUtils.startsWith(mi.getHref(), "javascript:") ? "" : mi.getHref());
        data.put("code", mi.getCode());
        data.put("iconCls", Strings.nullToEmpty(mi.getIconCls()));
        data.put("sortNo", mi.getSortNo());
        return data;
    }

}
