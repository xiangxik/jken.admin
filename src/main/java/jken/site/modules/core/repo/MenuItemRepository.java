package jken.site.modules.core.repo;

import jken.site.modules.core.entity.MenuItem;
import jken.site.support.data.jpa.QuerydslTreeRepository;

public interface MenuItemRepository extends QuerydslTreeRepository<MenuItem, Long> {
}
