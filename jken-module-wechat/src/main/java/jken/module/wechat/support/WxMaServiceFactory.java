package jken.module.wechat.support;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import jken.module.wechat.entity.Miniprogram;
import jken.module.wechat.repo.MiniprogramRepository;

import java.util.HashMap;
import java.util.Map;

public class WxMaServiceFactory {

    private final Map<Long, WxMaService> SERVICES = new HashMap<>();

    private final MiniprogramRepository miniprogramRepository;

    public WxMaServiceFactory(MiniprogramRepository miniprogramRepository) {
        this.miniprogramRepository = miniprogramRepository;
    }

    public WxMaService get(Long id) {
        WxMaService service = SERVICES.get(id);
        if (service == null) {
            synchronized (this) {
                if (service == null) {
                    service = buildService(id);
                    if (service != null) {
                        SERVICES.put(id, service);
                    }
                }
            }
        }
        return service;
    }

    protected WxMaService buildService(Long id) {
        Miniprogram setting = miniprogramRepository.findById(id).orElseThrow(RuntimeException::new);

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(setting.getAppid());
        config.setSecret(setting.getSecret());
        config.setToken(setting.getToken());
        config.setAesKey(setting.getAesKey());
        config.setMsgDataFormat(setting.getMsgDataFormat());

        WxMaServiceImpl service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
