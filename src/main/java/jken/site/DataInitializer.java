package jken.site;

import jken.site.modules.core.entity.Corp;
import jken.site.modules.core.entity.User;
import jken.site.modules.core.service.CorpService;
import jken.site.modules.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CorpService corpService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private volatile boolean nonExecuted = true;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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
            Corp wlCorp = createCorp("广州当凌信息科技有限公司", "wl000");
            createAdmin("admin", "qwe123", wlCorp.getCode());

            Corp wyCorp = createCorp("广州微禹信息科技有限公司", "wy000");
            createAdmin("admin", "qwe123", wyCorp.getCode());
        }
    }

    private Corp createCorp(String name, String code) {
        Corp corp = corpService.createNew();
        corp.setName(name);
        corp.setCode(code);
        return corpService.save(corp);
    }

    private User createAdmin(String username, String password, String corpCode) {
        User user = userService.createNew();
        user.setName("");
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setCorpCode(corpCode);
        return userService.save(user);
    }
}
