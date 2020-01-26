package jken.site.endpoint;

import jken.site.modules.core.service.MenuItemService;
import jken.site.support.data.TreeHelper;
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
