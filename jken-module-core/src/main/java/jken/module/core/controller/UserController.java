/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-05T19:35:47.651+08:00
 */

package jken.module.core.controller;

import com.querydsl.core.types.Predicate;
import jken.module.core.entity.Role;
import jken.module.core.entity.User;
import jken.support.web.CrudController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends CrudController<User, Long> {

    @Override
    public Page<User> list(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    @Override
    protected void onShowEdit(User entity, Model model) {
        super.onShowEdit(entity, model);

        model.addAttribute("entityRoleIds", entity.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
    }
}
