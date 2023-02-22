package com.iprody.source.code.flow.manager.audit.logging;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class to test advices invocations from Logging aspect class.
 * There are only methods which are used for tests.
 */
@Component
public class TestClassForLoggingAspect {
    /**
     * This function is a void method with a log operation annotation.
     */
    @LogOperation
    public void voidMethodWithLogOperationAnnotation() {
    }

    /**
     * Method returns an `Object` and is Annotated with `@LogOperation with name attribute.
     *
     * @return just null;
     */
    @LogOperation(name = "")
    public Object methodWithLogOperationAnnotation() {
        return new Object();
    }

    /**
     * Method to check if exception was found by class afterThrowing logging aspect.
     */
    @LogOperation
    public void methodWithLogOperationAnnotationThrowsException() throws Exception {
        throw new Exception();
    }

    /**
     * This method shouldn't be called because doesn't have @LogOperation annotation.
     */
    public void methodWithoutLogOperationAnnotation() {
    }

    /**
     * This method takes arguments and returns nothing.
     * It is used for checking arguments writing in logs
     *
     * @param list A list of strings
     */
    @LogOperation(name = "TestOperationName")
    public void voidMethodWithArgumentsWithLogOperationAnnotation(List<String> list) {
    }
}
