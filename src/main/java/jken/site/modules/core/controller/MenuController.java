package jken.site.modules.core.controller;

import com.querydsl.core.types.Predicate;
import jken.site.modules.core.entity.MenuItem;
import jken.site.support.data.TreeHelper;
import jken.site.support.web.CrudController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController extends CrudController<MenuItem, Long> {

    @Override
    public Page<MenuItem> list(Predicate predicate, Pageable pageable) {
        Page<MenuItem> page = super.doInternalPage(predicate, pageable);
        return new PageImpl<>(TreeHelper.toTree(page.getContent()), page.getPageable(), page.getTotalElements());
    }

//    @GetMapping(value = "/addChild", produces = "text/html")
//    public String showDetailAddChild(@RequestParam(value = "parent", required = false) MenuItem parent, Model model) {
//        MenuItem entity = getService().createNew();
//        entity.setParent(parent);
//        return showDetailEdit(entity, model);
//    }
}
