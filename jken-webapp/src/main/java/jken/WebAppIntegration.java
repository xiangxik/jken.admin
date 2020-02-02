/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-02T21:11:00.887+08:00
 */

package jken;

import jken.integration.AbstractModuleIntegration;
import jken.integration.ModuleMetadata;
import jken.integration.ModuleMetadataBuilder;

public class WebAppIntegration extends AbstractModuleIntegration {

    @Override
    public ModuleMetadata integrate() {
        ModuleMetadataBuilder builder = new ModuleMetadataBuilder("webapp");


        return builder.build();
    }
}
