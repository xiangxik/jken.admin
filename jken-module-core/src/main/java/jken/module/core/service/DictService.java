/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T18:21:24.118+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.Dict;
import jken.module.core.entity.DictItem;
import jken.support.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DictService extends CrudService<Dict, Long> {
    @Override
    public <S extends Dict> S save(S entity) {
        List<DictItem> items = entity.getItems();
        items.forEach(item -> item.setDict(entity));
        return super.save(entity);
    }
}
