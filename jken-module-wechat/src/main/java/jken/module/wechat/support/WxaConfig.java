package jken.module.wechat.support;

import cn.binarywang.wx.miniapp.api.WxMaService;
import jken.module.wechat.repo.WxaSettingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxaConfig {

    @Bean
    public WxMaService wxMaService(WxaSettingRepository wxaSettingRepository) {
        return new WxMaServiceCorpDelegate(wxaSettingRepository);
    }

}
