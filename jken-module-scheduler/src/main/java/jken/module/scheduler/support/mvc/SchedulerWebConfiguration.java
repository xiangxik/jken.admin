package jken.module.scheduler.support.mvc;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SchedulerWebConfiguration implements WebMvcConfigurer {

    private final ApplicationContext context;
    private final ObjectFactory<ConversionService> conversionService;

    public SchedulerWebConfiguration(ApplicationContext context,
                                     @Qualifier("mvcConversionService") ObjectFactory<ConversionService> conversionService) {
        this.context = context;
        this.conversionService = conversionService;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        FormattingConversionService conversionService = (FormattingConversionService) registry;

        JobConverter<FormattingConversionService> converter = new JobConverter<>(conversionService);
        converter.setApplicationContext(context);
    }
}
