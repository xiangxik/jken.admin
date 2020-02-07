/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-03T20:13:33.764+08:00
 */

package jken.module.core;

import jken.integration.AbstractModuleIntegration;
import jken.integration.Authority;
import jken.integration.ModuleMetadata;
import jken.integration.ModuleMetadataDecorator;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

@Order(0)
public class CoreModuleIntegration extends AbstractModuleIntegration {

    @Override
    public void integrate(ModuleMetadata metadata) {
        ModuleMetadataDecorator decorator = new ModuleMetadataDecorator(metadata);
        decorator.name("core");
        decorator.domainCrud("公司", "corp", "/corp");
        decorator.domainCrud("角色", "role", "/role")
                .addAuthority("查看用户", "role-view-user", new String[]{"/role/*/user"}, Authority.PatternType.ANT, HttpMethod.GET)
                .addAuthority("修改用户", "role-edit-user", new String[]{"/role/*/user"}, Authority.PatternType.ANT, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                .addAuthority("查看权限", "role-view-authority", new String[]{"/role/*/authority"}, Authority.PatternType.ANT, HttpMethod.GET)
                .addAuthority("修改权限", "role-edit-authority", new String[]{"/role/*/authority"}, Authority.PatternType.ANT, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);
        decorator.domainCrud("菜单", "menu", "/menu");
        decorator.domainCrud("用户", "user", "/user");
        decorator.domainCrud("字典", "dict", "/dict");
    }
}
