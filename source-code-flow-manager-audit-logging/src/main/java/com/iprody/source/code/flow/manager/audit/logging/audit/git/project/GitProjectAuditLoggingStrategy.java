package com.iprody.source.code.flow.manager.audit.logging.audit.git.project;

import com.iprody.source.code.flow.manager.audit.logging.audit.Audit;
import com.iprody.source.code.flow.manager.audit.logging.audit.AuditLoggingStrategy;
import com.iprody.source.code.flow.manager.audit.logging.audit.ParameterUtils;
import com.iprody.source.code.flow.manager.audit.logging.audit.ProjectAuditLog;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

@RequiredArgsConstructor
public class GitProjectAuditLoggingStrategy implements AuditLoggingStrategy {
    /**
     * Injecting the GitProjectArgumentContainer into the strategy.
     */
    private final GitProjectArgumentContainer container;

    /**
     * Creating an audit log based on the information provided by the JoinPoint and GitProject.
     *
     * @param joinPoint  the {@code JoinPoint} object that represents the annotated method call
     * @return a new {@code ProjectAuditLog} object that contains the audit information for the annotated method call
     */
    @Override
    public ProjectAuditLog execute(JoinPoint joinPoint) {
        final var signature = (MethodSignature) joinPoint.getSignature();

        final Audit audit = new Audit();
        audit.setProject(container.project());
        audit.setExecutor(container.project().getGitAdministrator());
        audit.setProvider(container.project().getGitProvider());
        audit.setParameters(ParameterUtils.getParameters(signature.getParameterNames(),
                joinPoint.getArgs()));

        final ProjectAuditLog projectAuditLog = new ProjectAuditLog();
        projectAuditLog.setProjectId(container.project());
        projectAuditLog.setAudit(audit);

        return projectAuditLog;
    }
}
