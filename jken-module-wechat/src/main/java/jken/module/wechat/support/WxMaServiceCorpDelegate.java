package jken.module.wechat.support;

import cn.binarywang.wx.miniapp.api.*;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import jken.module.wechat.entity.WxaSetting;
import jken.module.wechat.repo.WxaSettingRepository;
import jken.security.CorpCodeHolder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;

import java.util.HashMap;
import java.util.Map;

public class WxMaServiceCorpDelegate implements WxMaService {

    private final Map<String, WxMaService> CORP_SERVICE = new HashMap<>();

    private final WxaSettingRepository wxaSettingRepository;

    public WxMaServiceCorpDelegate(WxaSettingRepository wxaSettingRepository) {
        this.wxaSettingRepository = wxaSettingRepository;
    }

    private WxMaService getDelegate() {
        String corpCode = CorpCodeHolder.getCurrentCorpCode();
        WxMaService service = CORP_SERVICE.get(corpCode);
        if (service == null) {
            synchronized (this) {
                if (service == null) {
                    service = buildService(corpCode);
                    if (service != null) {
                        CORP_SERVICE.put(corpCode, service);
                    }
                }
            }
        }
        return service;
    }

    protected WxMaService buildService(String corpCode) {
        WxaSetting setting = wxaSettingRepository.findByCorpCode(corpCode);
        if (setting == null) {
            throw new RuntimeException("setting is null");
        }

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

    @Override
    public WxMaJscode2SessionResult jsCode2SessionInfo(String jsCode) throws WxErrorException {
        return getDelegate().jsCode2SessionInfo(jsCode);
    }

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        return getDelegate().checkSignature(timestamp, nonce, signature);
    }

    @Override
    public String getAccessToken() throws WxErrorException {
        return getDelegate().getAccessToken();
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        return getDelegate().getAccessToken(forceRefresh);
    }

    @Override
    public String getPaidUnionId(String openid, String transactionId, String mchId, String outTradeNo) throws WxErrorException {
        return getDelegate().getPaidUnionId(openid, transactionId, mchId, outTradeNo);
    }

    @Override
    public String get(String url, String queryParam) throws WxErrorException {
        return getDelegate().get(url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws WxErrorException {
        return getDelegate().post(url, postData);
    }

    @Override
    public String post(String url, Object obj) throws WxErrorException {
        return getDelegate().post(url, obj);
    }

    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        return getDelegate().execute(executor, uri, data);
    }

    @Override
    public void setRetrySleepMillis(int retrySleepMillis) {
        getDelegate().setRetrySleepMillis(retrySleepMillis);
    }

    @Override
    public void setMaxRetryTimes(int maxRetryTimes) {
        getDelegate().setMaxRetryTimes(maxRetryTimes);
    }

    @Override
    public WxMaConfig getWxMaConfig() {
        return getDelegate().getWxMaConfig();
    }

    @Override
    public void setWxMaConfig(WxMaConfig wxConfigProvider) {
        getDelegate().setWxMaConfig(wxConfigProvider);
    }

    @Override
    public WxMaMsgService getMsgService() {
        return getDelegate().getMsgService();
    }

    @Override
    public WxMaMediaService getMediaService() {
        return getDelegate().getMediaService();
    }

    @Override
    public WxMaUserService getUserService() {
        return getDelegate().getUserService();
    }

    @Override
    public WxMaQrcodeService getQrcodeService() {
        return getDelegate().getQrcodeService();
    }

    @Override
    @Deprecated
    public WxMaTemplateService getTemplateService() {
        return getDelegate().getTemplateService();
    }

    @Override
    public WxMaSubscribeService getSubscribeService() {
        return getDelegate().getSubscribeService();
    }

    @Override
    public WxMaAnalysisService getAnalysisService() {
        return getDelegate().getAnalysisService();
    }

    @Override
    public WxMaCodeService getCodeService() {
        return getDelegate().getCodeService();
    }

    @Override
    public WxMaJsapiService getJsapiService() {
        return getDelegate().getJsapiService();
    }

    @Override
    public WxMaSettingService getSettingService() {
        return getDelegate().getSettingService();
    }

    @Override
    public WxMaShareService getShareService() {
        return getDelegate().getShareService();
    }

    @Override
    public WxMaRunService getRunService() {
        return getDelegate().getRunService();
    }

    @Override
    public WxMaSecCheckService getSecCheckService() {
        return getDelegate().getSecCheckService();
    }

    @Override
    public WxMaPluginService getPluginService() {
        return getDelegate().getPluginService();
    }

    @Override
    public void initHttp() {
        getDelegate().initHttp();
    }

    @Override
    public RequestHttp getRequestHttp() {
        return getDelegate().getRequestHttp();
    }

    @Override
    public WxMaExpressService getExpressService() {
        return getDelegate().getExpressService();
    }

    @Override
    public WxMaCloudService getCloudService() {
        return getDelegate().getCloudService();
    }
}
