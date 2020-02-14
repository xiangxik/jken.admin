package jken.module.core.support.thymeleaf;

import jken.module.core.entity.DictItem;
import jken.module.core.service.DictService;
import jken.support.thymeleaf.ModuleExpressionObject;

import java.util.List;

@ModuleExpressionObject(objectName = "dict")
public class DictExpressionObject {

    private DictService dictService;

    public List<DictItem> items(String code) {
        return dictService.getItemsByCode(code);
    }

}
