package jken.site.security;

import com.google.common.base.Strings;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CorpCodeHolder {
    private static final ThreadLocal<String> HOLDER = new NamedThreadLocal<>("corp");

    public static String getCorpCode() {
        return HOLDER.get();
    }

    public static void setCorpCode(String corpCode) {
        HOLDER.set(corpCode);
    }

    public static void cleanup() {
        HOLDER.remove();
    }

    public static String getCurrentCorpCode() {
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
