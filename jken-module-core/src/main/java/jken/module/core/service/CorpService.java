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
import jken.integration.JkenModule;
import jken.module.core.entity.Corp;
import jken.module.core.entity.MenuItem;
import jken.module.core.entity.Role;
import jken.module.core.entity.User;
import jken.module.core.repo.CorpRepository;
import jken.support.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
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
        final List<MenuItem> menuItems = new ArrayList<>();
        IntegrationService.getModules().forEach(module -> buildMenus(menuItems, module.getMenus(), null, corpCode));
        return menuItems;
    }

    private void buildMenus(final List<MenuItem> result, List<JkenModule.Mi> mis, MenuItem parent, String corpCode) {
        Integer sortNo = 1;
        for (JkenModule.Mi mi : mis) {
            sortNo++;
            MenuItem menuItem = buildMenuItem(mi.getName(), mi.getCode(), mi.getHref(), mi.getIconCls(), mi.getAuthorities(), sortNo, corpCode, parent);
            result.add(menuItem);
            if (mi.getChildren() != null) {
                buildMenus(result, mi.getChildren(), menuItem, corpCode);
            }
        }
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
