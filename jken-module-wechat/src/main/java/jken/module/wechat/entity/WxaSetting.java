package jken.module.wechat.entity;

import jken.module.core.entity.User;
import jken.support.data.Corpable;
import jken.support.data.jpa.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_miniprogram")
public class WxaSetting extends DataEntity<User, Long> implements Corpable {

    private String appid;
    private String secret;
    private String token;
    private String aesKey;
    private String msgDataFormat;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getMsgDataFormat() {
        return msgDataFormat;
    }

    public void setMsgDataFormat(String msgDataFormat) {
        this.msgDataFormat = msgDataFormat;
    }

    @Column(length = 100, unique = true)
    private String corpCode;

    @Override
    public String getCorpCode() {
        return null;
    }

    @Override
    public void setCorpCode(String corpCode) {

    }
}
