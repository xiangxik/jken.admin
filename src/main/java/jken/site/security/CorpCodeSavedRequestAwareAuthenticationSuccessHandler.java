/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.853+08:00
 *
 */

package jken.site.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorpCodeSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private CookieGenerator cookieGenerator = new CookieGenerator();

    public CorpCodeSavedRequestAwareAuthenticationSuccessHandler() {
        cookieGenerator.setCookieName(CustomUserDetails.CORP_CODE_COOKIE);
        cookieGenerator.setCookieMaxAge(1209600 * 1000);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            cookieGenerator.addCookie(response, ((CustomUserDetails<?>) principal).getCorpCode());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
