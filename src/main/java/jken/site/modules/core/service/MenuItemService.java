package jken.site.modules.core.service;

import jken.site.modules.core.entity.MenuItem;
import jken.site.support.service.TreeService;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService extends TreeService<MenuItem, Long> {
}
