package jken.site;

import com.google.common.base.Strings;
import jken.site.modules.core.entity.Corp;
import jken.site.modules.core.entity.User;
import jken.site.modules.core.service.CorpService;
import jken.site.modules.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CorpService corpService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private volatile boolean nonExecuted = true;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (nonExecuted) {
            synchronized (this) {
                if (nonExecuted) {
                    nonExecuted = false;

                    doInitialize();
                }
            }
        }
    }

    private void doInitialize() {
        if (corpService.count() == 0) {
            Corp wlCorp = createCorp("广州当凌信息科技有限公司", "wl000", null, "http://www.whenling.com", "");
            createAdmin("admin", "qwe123", wlCorp.getCode());
            for (int i = 0; i < 100; i++) {
                createUser("user" + Strings.padStart(String.valueOf(i), 2, '0'), "qwe123", wlCorp.getCode(), "用户", i);
            }

            Corp wyCorp = createCorp("广州微禹信息科技有限公司", "wy000", null, "http://yisongshui.com", "");
            createAdmin("admin", "qwe123", wyCorp.getCode());
            for (int i = 0; i < 100; i++) {
                createUser("user" + Strings.padStart(String.valueOf(i), 2, '0'), "qwe123", wyCorp.getCode(), "用户", i);
            }
        }
    }

    private Corp createCorp(String name, String code, String logo, String website, String introduction) {
        Corp corp = corpService.createNew();
        corp.setName(name);
        corp.setCode(code);
        corp.setLogo(logo);
        corp.setWebsite(website);
        corp.setIntroduction(introduction);
        corp.setDisabled(false);
        corp.setStatus(Corp.Status.NORMAL);
        return corpService.save(corp);
    }

    private User createAdmin(String username, String password, String corpCode) {
        User user = userService.createNew();
        user.setName("管理员");
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setCorpCode(corpCode);
        return userService.save(user);
    }

    private User createUser(String username, String password, String corpCode, String name, int i) {
        User user = userService.createNew();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setMail(username + "@whenling.com");
        user.setMobile("13288888" + Strings.padStart(String.valueOf(i), 2, '0'));
        user.setCorpCode(corpCode);
        return userService.save(user);
    }
}
