package jken.module.wechat.web;

import com.querydsl.core.types.Predicate;
import jken.module.wechat.entity.WxaSetting;
import jken.module.wechat.service.WxaSettingService;
import jken.support.web.EntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/wxaSetting")
public class WxaSettingController extends EntityController<WxaSetting, Long> {

    @Autowired
    private WxaSettingService wxaSettingService;

    @Override
    public Page<WxaSetting> list(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }

    @GetMapping(value = "/info", produces = "text/html")
    public String showInfo(Model model) {
        model.addAttribute("entity", wxaSettingService.getCurrent());
        return getViewDir() + "/info";
    }

    @PutMapping(value = "/info")
    @ResponseBody
    public void saveInfo(@ModelAttribute @Valid WxaSetting entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("validate error");
        }

        WxaSetting current = wxaSettingService.getCurrent();

        current.setAppid(entity.getAppid());
        current.setSecret(entity.getSecret());
        current.setAesKey(entity.getAesKey());
        current.setMsgDataFormat(entity.getMsgDataFormat());
        current.setToken(entity.getToken());

        getService().save(current);
    }
}
