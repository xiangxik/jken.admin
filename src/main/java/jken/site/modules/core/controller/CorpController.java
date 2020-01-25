package jken.site.modules.core.controller;

import com.querydsl.core.types.Predicate;
import jken.site.modules.core.entity.Corp;
import jken.site.support.web.CrudController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/corp")
public class CorpController extends CrudController<Corp, Long> {

    @Override
    public Page<Corp> list(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
