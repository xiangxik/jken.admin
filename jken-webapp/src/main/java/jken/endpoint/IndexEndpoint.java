/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.515+08:00
 */

package jken.endpoint;

import jken.module.core.service.MenuItemService;
import jken.support.data.TreeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexEndpoint {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping({"", "/"})
    public String indexPage(Model model) {
        model.addAttribute("mis", TreeHelper.toTree(menuItemService.findAll()));
        return "/index";
    }

    @GetMapping({"/home"})
    public String homePage() {
        return "/home";
    }
}
