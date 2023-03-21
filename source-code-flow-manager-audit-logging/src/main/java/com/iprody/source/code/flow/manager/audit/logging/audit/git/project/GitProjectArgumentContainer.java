package com.iprody.source.code.flow.manager.audit.logging.audit.git.project;

import com.iprody.source.code.flow.manager.audit.logging.audit.ArgumentContainer;
import com.iprody.source.code.flow.manager.data.access.entity.GitProject;

/**
 * Immutable data class that holds a GitProject instance for the GitProjectAuditLoggingStrategy.
 *
 * @param project The GitProject instance to be held by this container.
 */
public record GitProjectArgumentContainer(GitProject project) implements ArgumentContainer {
}
