package jken.module.wechat.repo;

import jken.module.wechat.entity.WxaSetting;
import jken.support.data.jpa.QuerydslEntityRepository;

public interface WxaSettingRepository extends QuerydslEntityRepository<WxaSetting, Long> {

    WxaSetting findByCorpCode(String corpCode);

}
