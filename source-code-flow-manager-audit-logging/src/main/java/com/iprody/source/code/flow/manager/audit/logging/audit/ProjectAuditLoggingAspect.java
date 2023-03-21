package com.iprody.source.code.flow.manager.audit.logging.audit;

import com.iprody.source.code.flow.manager.audit.logging.audit.git.GitStrategyMappingRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * This aspect is responsible for logging audit information for methods annotated with @ProjectLogOperation.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class ProjectAuditLoggingAspect {

    /**
     * Injecting the ProjectLogService into the aspect.
     */
    private final ProjectLogRepository repository;
    /**
     * Injecting the GitStrategyMappingRegistry into the aspect.
     */
    private final GitStrategyMappingRegistry registry;

    /**
     * A pointcut that matches any method that has the {@link ProjectLogOperation} annotation
     * and takes a GitProject parameter.
     *
     * @param arg the parameter
     */
    @Pointcut(value = "args(arg,..) &&  @annotation(ProjectLogOperation)")
    private void methodsWithAuditLogOperationAnnotation(Object arg) {
    }

    /**
     * After advice that saves the audit log after a method annotated
     * with @ProjectLogOperation is successfully executed.
     * <p>
     * It creates the audit log and passes it to the ProjectLogService for persistence.
     *
     * @param joinPoint the {@link JoinPoint} object that represents the current join point
     * @param arg       the object where the annotated method was called
     */
    @After(value = "methodsWithAuditLogOperationAnnotation(arg)", argNames = "joinPoint,arg")
    @Transactional
    public void saveAfterExecutionAdvice(JoinPoint joinPoint, Object arg) {
        repository.save(registry.lookup(arg).execute(joinPoint));
    }

    /**
     * After throwing advice that saves the audit log when a method annotated with @ProjectLogOperation
     * throws an exception.
     * It creates the audit log and passes it to the ProjectLogService for persistence.
     * <p>
     *
     * @param joinPoint the {@link JoinPoint} object that represents the current join point
     * @param arg the object where the annotated method was called
     * @param exception the exception that was thrown by the annotated method
     */
    @AfterThrowing(value = "methodsWithAuditLogOperationAnnotation(arg)", throwing = "exception")
    @Transactional
    public void afterThrowingException(JoinPoint joinPoint, Object arg, Exception exception) {
        repository.save(registry.lookup(arg).execute(joinPoint));
    }
}
