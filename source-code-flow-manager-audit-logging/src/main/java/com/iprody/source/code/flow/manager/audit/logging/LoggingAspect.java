package com.iprody.source.code.flow.manager.audit.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This aspect class logs method calls upon detecting @LogOperation: {@link LogOperation}.
 * <p>
 * Stages to be logged:
 * 1) before execution a method,
 * 2) after execution a method,
 * 3) after returning a result,
 * 4) after throwing an exception.
 * Aspect uses a logger of a class from which one method was called.
 * If that class doesn't have its own logger, aspect logger uses his default logger.
 * Notice that only object methods which are called from another objects can be logged because of AOP.
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut matches any method that has the LogOperation annotation.
     */
    @Pointcut("@annotation(LogOperation)")
    private void methodsWithLogOperationAnnotation() {
    }

    /**
     * Pointcut matches any public method without returning.
     */
    @Pointcut("execution(public void *(..))")
    private void methodsWithoutReturn() {
    }

    /**
     * Advice that is executed before all the other advices in this aspect.
     * It is used for getting information about which logger is used for logging:
     * Logger from class of method to logging or default logger from this aspect class.
     *
     * @param joinPoint This is the object that holds dynamic information about the join point.
     */
    @Before("methodsWithLogOperationAnnotation()")
    @Order(10)
    public void beforeAllAspectsAdvice(JoinPoint joinPoint) {
        final String operationName = getOperationName(joinPoint);
        final String className = joinPoint.getTarget().getClass().getName();
        final Optional<Logger> logger = getLoggingOperationLogger(joinPoint);

        if (logger.isEmpty()) {
            log.info("Operation {} from {} does not have its own logger. Default logger will be used instead",
                    operationName, className);
        }
    }

    /**
     * This logging function is called before the execution of any method annotated with the `@LogOperation` annotation.
     *
     * @param joinPoint The join point is the point in the code where the advice is applied.
     */
    @Before("methodsWithLogOperationAnnotation()")
    @Order(20)
    public void beforeExecutionMethodWithLogOperationAnnotationAdvice(JoinPoint joinPoint) {
        final String methodName = getLoggedMethodName(joinPoint);
        final String methodArguments = getLoggedMethodArguments(joinPoint).orElse("");
        final Logger logger = getLoggingOperationLogger(joinPoint).orElse(log);

        logger.info("Before operation {} execution with arguments: [{}]", methodName, methodArguments);
    }

    /**
     * After the execution of a method annotated with @LogOperation, logs the method name and arguments.
     *
     * @param joinPoint The join point is the point in the code where the advice is applied.
     */
    @After("methodsWithLogOperationAnnotation()")
    @Order(30)
    public void afterExecutionMethodWithLogOperationAnnotationAdvice(JoinPoint joinPoint) {
        final String methodName = getLoggedMethodName(joinPoint);
        final String methodArguments = getLoggedMethodArguments(joinPoint).orElse("");
        final Logger logger = getLoggingOperationLogger(joinPoint).orElse(log);

        logger.info("After operation {} execution with arguments: [{}]", methodName, methodArguments);
    }

    /**
     * Logs the result of a method annotated with `@LogOperation` after it has returned.
     *
     * @param joinPoint The join point is the point in the code where the advice is applied.
     */
    @AfterReturning("methodsWithLogOperationAnnotation() && !methodsWithoutReturn()")
    @Order(40)
    public void afterReturningResultMethodWithLogOperationAnnotationAdvice(JoinPoint joinPoint) {
        final String methodName = getLoggedMethodName(joinPoint);

        final String methodArguments = getLoggedMethodArguments(joinPoint).orElse("");
        final Logger logger = getLoggingOperationLogger(joinPoint).orElse(log);

        logger.info("After operation {} execution produced result with arguments: [{}]",
                methodName, methodArguments);
    }

    /**
     * It logs the exception thrown by the method annotated with @LogOperation.
     *
     * @param joinPoint The join point is the point in the code where the advice is applied.
     * @param exception The exception that was thrown.
     */
    @AfterThrowing(value = "methodsWithLogOperationAnnotation()", throwing = "exception")
    @Order(50)
    public void afterThrowingExceptionMethodWithLogOperationAnnotationAdvice(JoinPoint joinPoint, Throwable exception) {
        final String methodName = getLoggedMethodName(joinPoint);
        final String methodArguments = getLoggedMethodArguments(joinPoint).orElse("");
        final Logger logger = getLoggingOperationLogger(joinPoint).orElse(log);
        final String exceptionName = exception.getClass().getName();
        final String exceptionMessage = exception.getMessage();

        logger.info("Operation {} failed with cause {}: \"{}\" based on arguments: [{}]",
                methodName, exceptionName, exceptionMessage, methodArguments);
    }

    /**
     * If the annotation has a name parameter, uses that, otherwise uses the method name.
     *
     * @param joinPoint The join point is the point in the code where the advice is applied.
     * @return The name of the operation.
     */
    private String getOperationName(JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String nameFromLogOperationAnnotation = methodSignature.getMethod()
                .getAnnotation(LogOperation.class).name();
        final String methodName = methodSignature.getMethod().getName();

        return nameFromLogOperationAnnotation.isEmpty()
                ? methodName : nameFromLogOperationAnnotation;
    }

    /**
     * It gets the method name from the `LogOperation` annotation, if it's not empty.
     * Otherwise, it gets the full qualified method name
     *
     * @param joinPoint The join point is the point in the code where the method is being called.
     * @return The method name.
     */
    private String getLoggedMethodName(JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String nameFromLogOperationAnnotation = methodSignature.getMethod()
                .getAnnotation(LogOperation.class).name();
        final String fullQualifiedMethodName = getFullQualifiedMethodName(joinPoint, methodSignature);

        return nameFromLogOperationAnnotation.isEmpty()
                ? fullQualifiedMethodName : nameFromLogOperationAnnotation;
    }

    /**
     * Returns the full qualified method name of the method that is being intercepted.
     *
     * @param joinPoint       This is the object that holds dynamic information about the join point.
     * @param methodSignature This is the signature of the method that is being intercepted.
     * @return The package name, class name, and method name of the method being called in the one String.
     */
    private String getFullQualifiedMethodName(JoinPoint joinPoint, MethodSignature methodSignature) {
        return joinPoint.getTarget().getClass().getName() + "."
                + methodSignature.getMethod().getName();
    }

    /**
     * It takes the arguments of the method being logged.
     * Converts these arguments to a string, and joins them together with commas
     *
     * @param joinPoint The JoinPoint object is the object that holds the information about
     *                  the method that is being called.
     * @return Optional<String>
     */
    private Optional<String> getLoggedMethodArguments(JoinPoint joinPoint) {
        final Object[] methodArguments = joinPoint.getArgs();
        if (methodArguments.length == 0) {
            return Optional.empty();
        }

        return Optional.of(Stream.of(methodArguments)
                .map(this::toStringView)
                .collect(Collectors.joining(", ")));
    }

    /**
     * If the object has overridden toString(), returns the result of toString(), otherwise returns the class name.
     *
     * @param arg The object to be tested.
     * @return The toString() method of the object.
     */
    private String toStringView(Object arg) {
        return isToStringOverridden(arg)
                ? arg.toString() : arg.getClass().getName();
    }

    /**
     * If the toString() method of the object does not contain the '@' character it means it was overridden.
     *
     * @param object The object to be checked.
     * @return true if it has overridden toString() method.
     */
    private boolean isToStringOverridden(Object object) {
        return !object.toString().contains("@");
    }

    /**
     * Gets a logger of the class with the logged method using reflection, and then returns the logger field.
     *
     * @param joinPoint The JoinPoint object that contains the information about the method that is being logged.
     * @return a logger of the class that contains the method should be logged or null if it hasn't its own logger.
     */
    private Optional<Logger> getLoggingOperationLogger(JoinPoint joinPoint) {
        return getLoggerField(joinPoint)
                .map(field -> {
                    field.setAccessible(true);
                    return getClassNameFromLogOperationAnnotatedOperation(joinPoint, field);
                }).orElse(Optional.empty());
    }

    /**
     * Try to get the log field from the target class, and if it is impossible, returns an empty Optional.
     *
     * @param joinPoint The join point is the point in the code where the aspect is applied.
     * @return An Optional<Field> object.
     */
    private Optional<Field> getLoggerField(JoinPoint joinPoint) {
        try {
            return Optional.of(joinPoint.getTarget().getClass().getDeclaredField("log"));
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }

    /**
     * It gets class name for the logger from the class that the method belongs.
     *
     * @param joinPoint   This is the object that holds the information about the method that is being called.
     * @param loggerField The field that is annotated with @LogOperation.
     * @return Optional<Logger>
     */
    private Optional<Logger> getClassNameFromLogOperationAnnotatedOperation(JoinPoint joinPoint, Field loggerField) {
        try {
            return Optional.of((Logger) loggerField.get(joinPoint.getTarget().getClass()));
        } catch (IllegalAccessException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }
}
