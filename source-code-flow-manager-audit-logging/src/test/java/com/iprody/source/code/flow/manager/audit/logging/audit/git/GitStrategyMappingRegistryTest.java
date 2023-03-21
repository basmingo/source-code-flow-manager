package com.iprody.source.code.flow.manager.audit.logging.audit.git;

import com.iprody.source.code.flow.manager.audit.logging.audit.GitStrategyMappingContext;
import com.iprody.source.code.flow.manager.audit.logging.audit.git.project.GitProjectAuditLoggingStrategy;
import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class GitStrategyMappingRegistryTest {

    private GitStrategyMappingRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new GitStrategyMappingRegistry();
    }

    @Test
    void shouldReturnGitStrategyMappingContext_whenObjectIsGitProject_StrategyFound() {
        final GitStrategyMappingContext context = registry.lookup(new GitProject());

        assertThat(context.getStrategy())
                .isExactlyInstanceOf(GitProjectAuditLoggingStrategy.class);
    }

    @Test
    void shouldThrowException_whenObjectIsGitProject_StrategyFound() {
        assertThatThrownBy(() ->
                registry.lookup(new Object()))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("No Strategy found for type: class java.lang.Object");
    }
}
