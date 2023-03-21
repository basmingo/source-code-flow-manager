package com.iprody.source.code.flow.manager.audit.logging.audit;

import org.aspectj.lang.JoinPoint;

/**
 * This interface defines the contract for an audit logging strategy that can be used
 * to log audit events for a Git project.
 */
public interface AuditLoggingStrategy {

    /**
     * Executes the audit logging strategy for the given data object.
     *
     * @param joinPoint
     * @return a ProjectAuditLog object
     */
    ProjectAuditLog execute(JoinPoint joinPoint);
}

