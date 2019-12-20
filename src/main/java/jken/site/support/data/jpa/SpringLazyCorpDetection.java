/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.874+08:00
 *
 */

package jken.site.support.data.jpa;

import jken.site.support.data.CorpDetection;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringLazyCorpDetection implements CorpDetection {

    private volatile CorpDetection corpDetection;

    @Override
    public String getCurrentCorpCode() {
        if (corpDetection == null) {
            synchronized (this) {
                if (corpDetection == null) {
                    ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
                    if (context != null) {
                        corpDetection = context.getBean(CorpDetection.class);
                        if (corpDetection == null) {
                            corpDetection = new NullCorpDetection();
                        }
                    }
                }
            }

        }
        return corpDetection.getCurrentCorpCode();
    }

    static class NullCorpDetection implements CorpDetection {

        @Override
        public String getCurrentCorpCode() {
            return null;
        }
    }
}
