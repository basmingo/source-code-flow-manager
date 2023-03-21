package com.iprody.source.code.flow.manager.audit.logging.audit.git;

import com.iprody.source.code.flow.manager.audit.logging.audit.GitStrategyMappingContext;
import com.iprody.source.code.flow.manager.audit.logging.audit.git.project.GitProjectArgumentContainer;
import com.iprody.source.code.flow.manager.audit.logging.audit.git.project.GitProjectAuditLoggingStrategy;
import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import org.springframework.stereotype.Component;

/**
 * Component that looks up and returns the appropriate Git audit logging strategy
 * for a given argument object.
 */
@Component
public class GitStrategyMappingRegistry {

    /**
     * The GitStrategyMappingContext instance held by the registry.
     */
    private final GitStrategyMappingContext ctx = new GitStrategyMappingContext();

    /**
     * Returns the GitStrategyMappingContext instance for the appropriate strategy
     * based on the type of the given argument object.
     *
     * @param argumentObject The object whose type determines the appropriate strategy.
     * @return A GitStrategyMappingContext instance with the appropriate strategy for the given argument object.
     * @throws IllegalArgumentException if no strategy is found for the given argument object.
     */
    public GitStrategyMappingContext lookup(Object argumentObject) {
        if (argumentObject instanceof GitProject gitProject) {
            ctx.setStrategy(new GitProjectAuditLoggingStrategy(new GitProjectArgumentContainer(gitProject)));
            return ctx;
        }
        throw new IllegalArgumentException("No Strategy found for type: " + argumentObject.getClass());
    }
}
