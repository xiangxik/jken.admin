/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.856+08:00
 *
 */

package jken.site.security;

import com.google.common.base.Strings;
import jken.site.support.data.CorpDetection;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentCorpDetection implements CorpDetection {
    private static final ThreadLocal<String> corpCodeHolder = new NamedThreadLocal<>("corp");

    public static String getCorpCode() {
        return corpCodeHolder.get();
    }

    public static void setCorpCode(String corpCode) {
        corpCodeHolder.set(corpCode);
    }

    public static void cleanup() {
        corpCodeHolder.remove();
    }

    @Override
    public String getCurrentCorpCode() {

        String corpCode = getCorpCode();
        if (Strings.isNullOrEmpty(corpCode)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof CustomUserDetails) {
                    corpCode = ((CustomUserDetails<?>) principal).getCorpCode();
                    setCorpCode(corpCode);
                }
            }

        }

        return corpCode;
    }


}
