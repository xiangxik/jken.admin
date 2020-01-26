package jken.site.modules.core.controller;

import com.querydsl.core.types.Predicate;
import jken.site.modules.core.entity.MenuItem;
import jken.site.modules.core.service.MenuItemService;
import jken.site.support.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController extends CrudController<MenuItem, Long> {

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public Page<MenuItem> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

//    @GetMapping("/tree")
//    @ResponseBody
//    public List<MenuItem> getTree(Predicate predicate, Pageable pageable) {
//        return menuItemService.findAll(predicate);
//    }

//    @PostMapping("/tree")
//    @ResponseBody
//    public Tree<MenuItem> postTree(@RequestParam(value = "checked", required = false) MenuItem[] checked, Predicate predicate) {
//        Tree<MenuItem> tree = getTree(predicate);
//        if (checked != null && checked.length > 0) {
//            tree.setChecked(Sets.newHashSet(checked));
//            tree.makeCheckable();
//        }
//        return tree;
//    }
}
