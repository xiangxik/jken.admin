package jken.module.wechat.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import jken.module.wechat.support.WxMaServiceFactory;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/wxa")
public class MiniprogramEndpoint {

    @Autowired
    private WxMaServiceFactory wxMaServiceFactory;

    @GetMapping("/{id}")
    public String joinUp(@PathVariable("id") Long id, @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        WxMaService wxMaService = wxMaServiceFactory.get(id);
        if (wxMaService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "valid error";
    }

    @PostMapping("/{id}")
    public String service(@PathVariable("id") Long id, @RequestParam(name = "encrypt_type", required = false) String encryptType,
                          @RequestParam(name = "msg_signature", required = false) String msgSignature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestBody(required = false) String data) {
        WxMaService wxMaService = wxMaServiceFactory.get(id);
        WxMaConfig config = wxMaService.getWxMaConfig();

        boolean isJson = Objects.equals(config.getMsgDataFormat(), WxMaConstants.MsgDataFormat.JSON);
        boolean isAesEncrypt = Objects.equals("aes", encryptType);

        WxMaMessage inMessage = isAesEncrypt ? (isJson ? WxMaMessage.fromEncryptedJson(data, config) : WxMaMessage.fromEncryptedXml(data, config, timestamp, nonce, msgSignature)) :
                (isJson ? WxMaMessage.fromJson(data) : WxMaMessage.fromXml(data));

        System.out.println(inMessage.getContent());

        WxMaXmlOutMessage outMessage = new WxMaXmlOutMessage(inMessage.getFromUser(), inMessage.getToUser(), System.currentTimeMillis(), "text");

        return isAesEncrypt ? outMessage.toEncryptedXml(config) : outMessage.toXml();
    }

    @GetMapping("/{id}/authCode2Session")
    public String authCode2Session(@PathVariable("id") Long id, @RequestParam("code") String code) {
        WxMaService wxMaService = wxMaServiceFactory.get(id);
        WxMaJscode2SessionResult result = null;
        try {
            result = wxMaService.jsCode2SessionInfo(code);
            System.out.println("openid:" + result.getOpenid());
            System.out.println("sessionKey:" + result.getSessionKey());
            System.out.println("unionid:" + result.getUnionid());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("/{id}/sendSubscribe")
    public void sendSubscribeMessage(@PathVariable("id") Long id) {
        WxMaService wxMaService = wxMaServiceFactory.get(id);
        WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();
        subscribeMessage.setToUser("onRr74nyDCAeuD7vem0mKXfvW7UQ");
        subscribeMessage.setTemplateId("FcOZspWz6--7IWCiIgMYgWzy49AkzGNU4CF-pJYg6c8");
        subscribeMessage.setPage("pages/index/index");
        WxMaSubscribeMessage.Data thing1 = new WxMaSubscribeMessage.Data();
        thing1.setName("thing1");
        thing1.setValue("abc");
        subscribeMessage.addData(thing1);

        WxMaSubscribeMessage.Data thing2 = new WxMaSubscribeMessage.Data();
        thing2.setName("thing2");
        thing2.setValue("def");
        subscribeMessage.addData(thing2);

        try {
            wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

}
