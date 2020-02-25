package jken.module.wechat.service;

import jken.module.wechat.entity.WxaSetting;
import jken.module.wechat.repo.WxaSettingRepository;
import jken.security.CorpCodeHolder;
import jken.support.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxaSettingService extends CrudService<WxaSetting, Long> {

    @Autowired
    private WxaSettingRepository wxaSettingRepository;

    public WxaSetting getCurrent() {
        WxaSetting setting = wxaSettingRepository.findByCorpCode(CorpCodeHolder.getCurrentCorpCode());
        if (setting == null) {
            synchronized (this) {
                if (setting == null) {
                    setting = wxaSettingRepository.createNew();
                    setting.setCorpCode(CorpCodeHolder.getCurrentCorpCode());
                }
            }
        }
        return setting;
    }
}
