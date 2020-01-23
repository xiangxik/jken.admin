package jken.site.support.thymeleaf;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

@Component
public class JkenDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Jken Dialect";
    private static final String DIALECT_PREFIX = "jk";

    public JkenDialect() {
        super(DIALECT_NAME, DIALECT_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new RequiredModelProcessor(dialectPrefix));
        return processors;
    }
}
