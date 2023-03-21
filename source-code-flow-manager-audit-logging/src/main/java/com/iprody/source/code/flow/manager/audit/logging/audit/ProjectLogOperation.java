package com.iprody.source.code.flow.manager.audit.logging.audit;


import com.iprody.source.code.flow.manager.audit.logging.LogOperation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark methods that perform a logging operation on a {@code GitProject} object.
 * It serves as a marker for the logging aspect, which intercepts the annotated methods and creates an audit log
 * for them.
 * The annotation has a {@code name} attribute that can be used to specify a custom name for the log operation.
 * This annotation is a meta-annotation that inherits the {@code LogOperation} annotation, which provides
 * additional configuration for the logging aspect.
 *
 * @see LogOperation
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@LogOperation
public @interface ProjectLogOperation {

    /**
     * Attribute can be set to define specific operation name.
     * It isn't necessary to be set.
     *
     * @return name of the operation
     */
    String name() default "";
}
