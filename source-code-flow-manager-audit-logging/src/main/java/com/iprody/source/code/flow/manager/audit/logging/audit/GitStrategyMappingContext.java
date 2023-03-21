package com.iprody.source.code.flow.manager.audit.logging.audit;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

/**
 * The GitStrategyMappingContext class represents a context for mapping Git audit logging strategies.
 * It provides an instance of an AuditLoggingStrategy to be executed when needed.
 */
@Getter
@Setter
public class GitStrategyMappingContext {

    /**
     * The AuditLoggingStrategy instance held by the context, used to execute the strategy's implementation
     * of the ProjectAuditLog method.
     */
    private AuditLoggingStrategy strategy;

    /**
     * Executes the strategy's implementation of the ProjectAuditLog method with a JoinPoint parameter.
     *
     * @param joinPoint The JoinPoint to be used in the strategy's implementation of the ProjectAuditLog method.
     * @return The ProjectAuditLog returned by the strategy's implementation of the ProjectAuditLog method.
     */
    public ProjectAuditLog execute(JoinPoint joinPoint) {
        return strategy.execute(joinPoint);
    }
}
