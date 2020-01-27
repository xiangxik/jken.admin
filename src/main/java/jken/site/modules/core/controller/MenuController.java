package jken.site.modules.core.controller;

import com.querydsl.core.types.Predicate;
import jken.site.modules.core.entity.MenuItem;
import jken.site.support.web.CrudController;
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
@RequestMapping("/menu")
public class MenuController extends CrudController<MenuItem, Long> {

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

    private Map<String, Object> convertToTreeData(MenuItem mi) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", mi.getId());
        data.put("pId", mi.getParent() == null ? null : mi.getParent().getId());
        data.put("name", mi.getName());
        data.put("href", mi.getHref());
        data.put("code", mi.getCode());
        data.put("iconCls", mi.getIconCls());
        return data;
    }

}
