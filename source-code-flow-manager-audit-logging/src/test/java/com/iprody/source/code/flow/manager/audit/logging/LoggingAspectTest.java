package com.iprody.source.code.flow.manager.audit.logging;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * This class tests the LoggingAspect class.
 */
@SpringBootTest(classes = {TestClassForLoggingAspect.class, LoggingAspect.class})
class LoggingAspectTest {

    /**
     * Methods TestClass are used to test advices according to correct pointcuts of Logging aspect.
     */
    @Autowired
    private TestClassForLoggingAspect testClass;
    /**
     * Creating a mock object of the LoggingAspect class that spies for origin classes and copy actions for tests.
     */
    @SpyBean
    private LoggingAspect loggingAspect;

    /**
     * Test method checks that all the needed aspects were called from LoggingAspect
     * when public void method with LogOperation annotation was called.
     */
    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedVoidMethod() {
        testClass.voidMethodWithLogOperationAnnotation();

        verify(loggingAspect, times(1))
                .beforeAllAspectsAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .beforeExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .afterExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterReturningResultMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterThrowingExceptionMethodWithLogOperationAnnotationAdvice(Mockito.any(), Mockito.any());
    }

    /**
     * Test method checks that all the needed aspects were called from LoggingAspect
     * when public not void method with LogOperation annotation was called.
     */
    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedMethodWithReturn() {
        testClass.methodWithLogOperationAnnotation();

        verify(loggingAspect, times(1))
                .beforeAllAspectsAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .beforeExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .afterExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .afterReturningResultMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterThrowingExceptionMethodWithLogOperationAnnotationAdvice(Mockito.any(), Mockito.any());
    }

    /**
     * Test method checks that all the needed aspects were called from LoggingAspect
     * when public not void method with LogOperation annotation was called and threw some Exception.
     */
    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedMethodThrowsException() {
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> testClass.methodWithLogOperationAnnotationThrowsException());

        verify(loggingAspect, times(1))
                .beforeAllAspectsAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .beforeExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .afterExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterReturningResultMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .afterThrowingExceptionMethodWithLogOperationAnnotationAdvice(Mockito.any(), Mockito.any());
    }

    /**
     * Test method checks that none aspects were called from LoggingAspect
     * when public not void method without LogOperation annotation was called.
     */
    @Test
    void shouldNotInvokeLoggingAspectsOnNotAnnotatedMethodWithReturn() {
        testClass.methodWithoutLogOperationAnnotation();

        verify(loggingAspect, never())
                .beforeAllAspectsAdvice(Mockito.any());
        verify(loggingAspect, never())
                .beforeExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterReturningResultMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterThrowingExceptionMethodWithLogOperationAnnotationAdvice(Mockito.any(), Mockito.any());
    }

    /**
     * It verifies that the logging aspect is invoked before and after the execution of the method with.
     * There should be parameters of the logged method in the logs
     */
    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedMethodWithArguments() {
        testClass.voidMethodWithArgumentsWithLogOperationAnnotation(List.of("One", "Two", "Three"));

        verify(loggingAspect, times(1))
                .beforeAllAspectsAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .beforeExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, times(1))
                .afterExecutionMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterReturningResultMethodWithLogOperationAnnotationAdvice(Mockito.any());
        verify(loggingAspect, never())
                .afterThrowingExceptionMethodWithLogOperationAnnotationAdvice(Mockito.any(), Mockito.any());
    }

}
