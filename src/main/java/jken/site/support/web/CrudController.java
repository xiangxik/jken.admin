/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.895+08:00
 *
 */

package jken.site.support.web;

import com.querydsl.core.types.Predicate;
import jken.site.support.data.jpa.Entity;
import jken.site.support.service.CrudService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

public abstract class CrudController<T extends Entity<I>, I extends Serializable> extends BaseController implements InitializingBean {

    @Autowired
    private CrudService<T, I> service;

    private String viewDir;

    @Override
    public void afterPropertiesSet() {
        RequestMapping requestMapping = getClass().getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            viewDir = requestMapping.value()[0];
        }
    }

    @GetMapping("/page")
    @ResponseBody
    public abstract Page<T> doPage(Predicate predicate, Pageable pageable);

    protected Page<T> doInternalPage(Predicate predicate, Pageable pageable) {
        return getService().findAll(predicate, pageable);
    }

    @GetMapping({"", "/", "/index"})
    public String show(Model model) {
        onShowIndexPage(model);
        return getViewDir() + "/index";
    }

    @GetMapping("list")
    public String list(Model model) {
        onShowListPage(model);
        return getViewDir() + "/list";
    }

    @GetMapping("/select")
    public String select(Model model) {
        return getViewDir() + "/select";
    }

    @GetMapping({"/add", "/edit"})
    public String add(Model model) {
        return edit(getService().createNew(), model);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") T entity, Model model) {
        model.addAttribute("entity", entity);
        onShowEditPage(entity, model);
        return getViewDir() + "/edit";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") T entity, Model model) {
        model.addAttribute("entity", entity);
        onShowViewPage(entity, model);
        return getViewDir() + "/edit";
    }

    @PostMapping("/save/{entity}")
    @ResponseBody
    public void doSave(@ModelAttribute("entity") @Valid T entity, BindingResult bindingResult) {
        onValidate(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            //
        }

        onBeforeSave(entity);
        getService().save(entity);
        onAfterSave(entity);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void batchDelete(@RequestParam(value = "ids[]") T[] entities) {
        if (entities != null) {
            for (T entity : entities) {
                onDelete(entity);
            }
        }
    }

    protected void onDelete(T entity) {
        if (onBeforeDelete(entity)) {
            getService().delete(entity);
        }
    }

    @GetMapping("/info")
    @ResponseBody
    public T getInfo(@RequestParam("id") T entity) {
        return entity;
    }

    protected void onShowIndexPage(Model model) {
    }

    protected void onShowListPage(Model model) {
    }

    protected void onShowEditPage(T entity, Model model) {
    }

    protected void onShowViewPage(T entity, Model model) {
    }

    protected void onValidate(T entity, BindingResult bindingResult) {
    }

    protected void onBeforeSave(T entity) {
    }

    protected void onAfterSave(T entity) {
    }

    protected boolean onBeforeDelete(T entity) {
        return true;
    }

    public CrudService<T, I> getService() {
        return service;
    }

    public String getViewDir() {
        return viewDir;
    }

    public void setViewDir(String viewDir) {
        this.viewDir = viewDir;
    }
}
