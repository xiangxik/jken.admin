/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-02T21:11:00.881+08:00
 */

package jken.module.core;

import jken.integration.AbstractModuleIntegration;
import jken.integration.ModuleMetadata;
import jken.integration.ModuleMetadataBuilder;

public class CoreModuleIntegration extends AbstractModuleIntegration {

    @Override
    public ModuleMetadata integrate() {
        ModuleMetadataBuilder builder = new ModuleMetadataBuilder("core");
        builder.domainCrud("公司", "corp", "/corp");
        builder.domainCrud("角色", "role", "/role")
                .addAuthority("查看用户", "role-view-user", "/role/viewUser")
                .addAuthority("添加用户", "role-add-user", "/role/addUser")
                .addAuthority("移除用户", "role-remove-user", "/role/removeUser")
                .addAuthority("编辑权限", "role-edit-authority", "/role/updateAuthority");
        builder.domainCrud("菜单", "menu", "/menu");
        builder.domainCrud("用户", "user", "/user");
        return builder.build();
    }
}
