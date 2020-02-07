/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-07T18:21:24.118+08:00
 */

package jken.module.core.service;

import jken.module.core.entity.Dict;
import jken.module.core.entity.DictItem;
import jken.module.core.entity.QDictItem;
import jken.module.core.repo.DictItemRepository;
import jken.support.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DictService extends CrudService<Dict, Long> {

    @Autowired
    private DictItemRepository dictItemRepository;

    public <S extends Dict> S saveWithItems(S entity, List<DictItem> newItems) {
        S savedEntity = super.save(entity);

        Iterable<DictItem> oldItems = dictItemRepository.findAll(QDictItem.dictItem.dict.eq(entity));
        oldItems.forEach(oldItem -> {
            final Long oldId = oldItem.getId();
            if (!newItems.stream().anyMatch(item -> Objects.equals(item.getId(), oldId))) {
                dictItemRepository.delete(oldItem);
            }
        });
        newItems.forEach(item -> {
            item.setDict(savedEntity);
            dictItemRepository.save(item);
        });

        return savedEntity;
    }
}
