/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.485+08:00
 */

package jken.module.core.service;

import com.google.common.collect.Sets;
import jken.integration.Authority;
import jken.integration.IntegrationService;
import jken.module.core.entity.Corp;
import jken.module.core.entity.MenuItem;
import jken.module.core.entity.Role;
import jken.module.core.entity.User;
import jken.module.core.repo.CorpRepository;
import jken.support.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorpService extends CrudService<Corp, Long> {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorpRepository corpRepository;

    public Corp findByCode(String code) {
        return corpRepository.findByCode(code);
    }

    public Corp createNewCorp(String name, String code, String adminUsername, String adminPassword) {
        Corp corp = buildCorp(name, code);
        List<MenuItem> menuItems = buildMenuItems(code);
        Role role = buildAdminRole(code, menuItems);
        buildAdmin(code, role, adminUsername, adminPassword);
        return corp;
    }

    protected Corp buildCorp(String name, String code) {
        Corp corp = createNew();
        corp.setName(name);
        corp.setCode(code);
        corp.setStatus(Corp.Status.NORMAL);
        return save(corp);
    }

    protected List<MenuItem> buildMenuItems(String corpCode) {
        List<MenuItem> menuItems = new ArrayList<>();
        int sortNo = 0;

        MenuItem manage = buildMenuItem("系统管理", "manage", "javascript:;", "layui-icon-set", null, sortNo++, corpCode, null);
        menuItems.add(manage);

        MenuItem manageSet = buildMenuItem("基础设置", "set", "javascript:;", null, null, sortNo++, corpCode, manage);
        menuItems.add(manageSet);
        menuItems.add(buildMenuItem("公司信息", "corp_info", "corp/info", null, "corp-view-info,corp-edit-info", sortNo++, corpCode, manageSet));
        menuItems.add(buildMenuItem("菜单管理", "menu", "menu", null, "menu-list,menu-view,menu-add,menu-edit,menu-delete", sortNo++, corpCode, manageSet));
        menuItems.add(buildMenuItem("字典管理", "dict", "dict", null, "dict-list,dict-view,dict-add,dict-edit,dict-delete", sortNo++, corpCode, manageSet));

        MenuItem orgSet = buildMenuItem("人员管理", "member", "javascript:;", null, null, sortNo++, corpCode, manage);
        menuItems.add(orgSet);
        menuItems.add(buildMenuItem("用户管理", "user", "user", null, "user-list,user-view,user-add,user-edit,user-delete", sortNo++, corpCode, orgSet));
        menuItems.add(buildMenuItem("角色管理", "role", "role", null, "role-list,role-view,role-add,role-edit,role-delete,role-view-user,role-edit-user,role-view-authority,role-edit-authority", sortNo++, corpCode, orgSet));


        return menuItems;
    }

    protected Role buildAdminRole(String corpCode, List<MenuItem> menuItems) {
        Role role = roleService.createNew();
        role.setLocked(true);
        role.setName("管理员");
        role.setCode(Authority.AUTHORITY_ADMIN);
        role.setMenuItems(menuItems);
        role.setAuthorities(IntegrationService.getAuthorities().stream().map(Authority::getCode).collect(Collectors.toList()));
        role.setCorpCode(corpCode);
        roleService.save(role);
        return role;
    }

    protected User buildAdmin(String corpCode, Role adminRole, String username, String password) {
        User user = userService.createNew();
        user.setName("管理员");
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setLocked(true);
        user.setRoles(Sets.newHashSet(adminRole));
        user.setCorpCode(corpCode);
        return userService.save(user);
    }

    private MenuItem buildMenuItem(String name, String code, String href, String iconCls, String authorities, Integer sortNo, String corpCode, MenuItem parent) {
        MenuItem mi = menuItemService.createNew();
        mi.setName(name);
        mi.setCode(code);
        mi.setHref(href);
        mi.setIconCls(iconCls);
        mi.setCorpCode(corpCode);
        mi.setSortNo(sortNo * 100);
        mi.setAuthorities(authorities);
        mi.setParent(parent);
        menuItemService.save(mi);
        return mi;
    }

}
