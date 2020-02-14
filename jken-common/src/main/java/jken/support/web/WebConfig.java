/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-01T20:59:46.463+08:00
 */

package jken.support.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new GenericConverter() {
//            @Override
//            public Set<ConvertiblePair> getConvertibleTypes() {
//                return Collections.singleton(new ConvertiblePair(String.class, Class.class));
//            }
//
//            @Override
//            public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//                if (source == null || !StringUtils.hasText(source.toString())) {
//                    return null;
//                }
//
//                String src = (String) source;
//                try {
//                    return ClassUtils.forName(src, ClassUtils.getDefaultClassLoader());
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
//    }
}
