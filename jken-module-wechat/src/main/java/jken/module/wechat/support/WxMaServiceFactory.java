package jken.module.wechat.support;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import jken.module.wechat.entity.Miniprogram;
import jken.module.wechat.repo.MiniprogramRepository;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

public class WxMaServiceFactory {

    private final Map<String, WxMaService> SERVICES = new HashMap<>();

    private final MiniprogramRepository miniprogramRepository;

    public WxMaServiceFactory(MiniprogramRepository miniprogramRepository) {
        this.miniprogramRepository = miniprogramRepository;
    }

    public WxMaService get(String code, String corpCode) {
        String key = corpCode + '@' + code;
        WxMaService service = SERVICES.get(key);
        if (service == null) {
            synchronized (this) {
                if (service == null) {
                    service = buildService(code, corpCode);
                    if (service != null) {
                        SERVICES.put(key, service);
                    }
                }
            }
        }
        return service;
    }

    protected WxMaService buildService(String code, String corpCode) {
        Miniprogram setting = miniprogramRepository.findByCodeAndCorpCode(code, corpCode);

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(setting.getAppid());
        config.setSecret(setting.getSecret());
        config.setToken(setting.getToken());
        config.setAesKey(Base64.encodeBase64String(setting.getAesKey().getBytes()));
        config.setMsgDataFormat(setting.getMsgDataFormat());

        WxMaServiceImpl service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
