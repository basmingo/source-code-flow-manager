package com.iprody.source.code.flow.manager.audit.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation marks methods to be logged by LoggingAspect advices.
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LogOperation {

    /**
     * Attribute can be set to define specific operation name.
     * It isn't necessary to be set.
     *
     * @return name of the operation
     */
    String name() default "";
}
