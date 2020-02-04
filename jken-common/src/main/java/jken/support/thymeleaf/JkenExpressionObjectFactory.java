/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-04T15:00:37.480+08:00
 */

package jken.support.thymeleaf;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class JkenExpressionObjectFactory implements IExpressionObjectFactory {

    public static final String CURRENT_USER_EXPRESSION_OBJECT_NAME = "currentUser";

    protected static final Set<String> ALL_EXPRESSION_OBJECT_NAMES =
            Collections.unmodifiableSet(new LinkedHashSet<String>(java.util.Arrays.asList(
                    CURRENT_USER_EXPRESSION_OBJECT_NAME)));

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext iExpressionContext, String expressionObjectName) {
        if (CURRENT_USER_EXPRESSION_OBJECT_NAME.equals(expressionObjectName)) {
            return null;
        }

        return null;
    }

    @Override
    public boolean isCacheable(final String expressionObjectName) {
        return false;
    }
}
