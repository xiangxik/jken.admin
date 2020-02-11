/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.470+08:00
 */

package jken.module.core.controller;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import jken.module.core.entity.MenuItem;
import jken.support.web.TreeController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController extends TreeController<MenuItem, Long> {

    @Override
    public List<Object> list(@QuerydslPredicate(root = MenuItem.class) Predicate predicate, Sort sort) {
        return super.doList(predicate, sort);
    }

    @Override
    public List<Object> tree(@QuerydslPredicate(root = MenuItem.class) Predicate predicate, Sort sort) {
        return super.doTree(predicate, sort);
    }

    @Override
    protected void extraListConvert(Map<String, Object> data, MenuItem entity) {
        data.put("name", entity.getName());
        data.put("href", StringUtils.startsWith(entity.getHref(), "javascript:") ? "" : entity.getHref());
        data.put("code", entity.getCode());
        data.put("iconCls", Strings.nullToEmpty(entity.getIconCls()));
    }

    @Override
    protected String treeNodeDisplay(MenuItem entity) {
        return entity.getName();
    }

//    @GetMapping(value = "/select", produces = "application/json")
//    @ResponseBody
//    public Iterable<Map<String, Object>> list(@RequestParam(value = "selected[]", required = false) MenuItem[] selected, @RequestParam(value = "disabled[]", required = false) MenuItem[] disabled, @QuerydslPredicate(root = MenuItem.class) Predicate predicate) {
//        Iterable<MenuItem> roots = menuItemService.findAll(predicate);
//        List<MenuItem> entities = TreeHelper.toTree(Lists.newArrayList(roots));
//
//        return convertToSelect(entities, selected, disabled);
//    }
//
//    private Iterable<Map<String, Object>> convertToSelect(List<MenuItem> entities, MenuItem[] selected, MenuItem[] disabled) {
//        if (entities == null || entities.size() == 0) {
//            return null;
//        }
//        return entities.stream().map(entity -> {
//            Map<String, Object> data = new HashMap<>();
//            data.put("value", entity.getId());
//            data.put("name", entity.getName());
//            if (selected != null && ArrayUtils.contains(selected, entity)) {
//                data.put("selected", true);
//            }
//            if (disabled != null && ArrayUtils.contains(disabled, entity)) {
//                data.put("disabled", true);
//            }
//            data.put("children", convertToSelect(entity.getChildren(), selected, disabled));
//            return data;
//        }).collect(Collectors.toList());
//    }
}
