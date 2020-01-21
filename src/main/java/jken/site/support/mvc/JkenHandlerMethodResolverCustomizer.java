package jken.site.support.mvc;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Component;

@Component
public class JkenHandlerMethodResolverCustomizer implements PageableHandlerMethodArgumentResolverCustomizer, SortHandlerMethodArgumentResolverCustomizer {

    @Override
    public void customize(PageableHandlerMethodArgumentResolver pageableResolver) {
        pageableResolver.setOneIndexedParameters(true);
        pageableResolver.setPageParameterName("page");
        pageableResolver.setSizeParameterName("limit");
    }

    @Override
    public void customize(SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver) {

    }
}
