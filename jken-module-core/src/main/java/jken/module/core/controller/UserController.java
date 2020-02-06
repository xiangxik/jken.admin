/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.651+08:00
 */

package jken.module.core.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import jken.module.core.entity.Role;
import jken.module.core.entity.User;
import jken.support.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends CrudController<User, Long> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> list(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    @Override
    protected void onShowEdit(User entity, Model model) {
        super.onShowEdit(entity, model);
        model.addAttribute("entityRoleIds", entity.isNew() ? Lists.newArrayList() : entity.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
    }

    @Override
    protected void onValidate(User entity, BindingResult bindingResult) {
        super.onValidate(entity, bindingResult);
        if(entity.isNew()) {
            String rawPassword = getRequest().getParameter("newPassword");
            if(Strings.isNullOrEmpty(rawPassword)) {
                bindingResult.addError(new FieldError("entity", "password", "密码不能为空"));
            }
        }
    }

    @Override
    protected void onBeforeSave(User entity) {
        super.onBeforeSave(entity);

        String rawPassword = getRequest().getParameter("newPassword");
        if(!Strings.isNullOrEmpty(rawPassword)) {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            entity.setPassword(encodedPassword);
        }
    }
}
