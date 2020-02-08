/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.468+08:00
 */

package jken.module.core.controller;

import com.querydsl.core.types.Predicate;
import jken.module.core.entity.Corp;
import jken.module.core.service.CorpService;
import jken.security.CorpCodeHolder;
import jken.support.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/corp")
public class CorpController extends CrudController<Corp, Long> {

    @Autowired
    private CorpService corpService;

    @Override
    public Page<Corp> list(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    @GetMapping(value = "/info", produces = "text/html")
    public String showInfo(Model model) {
        String corpCode = CorpCodeHolder.getCurrentCorpCode();
        Corp currentCorp = corpService.findByCode(corpCode);
        model.addAttribute("entity", currentCorp);
        return getViewDir() + "/info";
    }

    @PutMapping(value = "/info")
    @ResponseBody
    public void saveInfo(@ModelAttribute @Valid Corp entity, BindingResult bindingResult) {
        String corpCode = CorpCodeHolder.getCurrentCorpCode();
        Corp currentCorp = corpService.findByCode(corpCode);

        currentCorp.setWebsite(entity.getWebsite());
        currentCorp.setIntroduction(entity.getIntroduction());

        getService().save(currentCorp);
    }
}
