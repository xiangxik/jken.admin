/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T21:44:55.235+08:00
 */

package jken.module.core;

import jken.integration.AbstractModuleIntegration;
import jken.integration.ModuleMetadata;

public class CoreModuleIntegration extends AbstractModuleIntegration {
    @Override
    public ModuleMetadata integrate() {
        System.out.println("core integration");
        return super.integrate();
    }
}
