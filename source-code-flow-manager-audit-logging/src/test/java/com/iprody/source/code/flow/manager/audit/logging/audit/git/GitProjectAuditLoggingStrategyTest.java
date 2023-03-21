package com.iprody.source.code.flow.manager.audit.logging.audit.git;

import com.iprody.source.code.flow.manager.audit.logging.audit.ProjectAuditLog;
import com.iprody.source.code.flow.manager.audit.logging.audit.git.project.GitProjectArgumentContainer;
import com.iprody.source.code.flow.manager.audit.logging.audit.git.project.GitProjectAuditLoggingStrategy;
import com.iprody.source.code.flow.manager.data.access.entity.GitAdministrator;
import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import com.iprody.source.code.flow.manager.data.access.entity.GitProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(value = {MockitoExtension.class, SoftAssertionsExtension.class})
public final class GitProjectAuditLoggingStrategyTest {

    /**
     * Tests that the {@link GitProjectAuditLoggingStrategy#execute(JoinPoint)} method correctly creates
     * a {@link ProjectAuditLog} object with the expected audit information.
     * @param softly
     */
    @Test
    public void testExecute_shouldCreateProjectAuditLogWithCorrectAuditInformation(SoftAssertions softly) {
        // Given
        final GitAdministrator administrator = new GitAdministrator();
        final GitProvider provider = new GitProvider();
        final GitProject gitProject = new GitProject();
        gitProject.setGitAdministrator(administrator);
        gitProject.setGitProvider(provider);
        gitProject.setId(1L);

        final GitProjectArgumentContainer container = new GitProjectArgumentContainer(gitProject);

        final GitProjectAuditLoggingStrategy strategy = new GitProjectAuditLoggingStrategy(container);

        final JoinPoint joinPoint = mock(JoinPoint.class);
        final MethodSignature signature = mock(MethodSignature.class);

        when(signature.getParameterNames()).thenReturn(new String[]{"param1", "param2"});
        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"value1", "value2"});

        // When
        final ProjectAuditLog result = strategy.execute(joinPoint);

        // Then
        softly.assertThat(result).isNotNull();
        softly.assertThat(result.getAudit()).isNotNull();
        softly.assertThat(result.getAudit().getExecutor()).isEqualTo(administrator);
        softly.assertThat(result.getAudit().getProvider()).isEqualTo(provider);
        softly.assertThat(result.getProjectId()).isEqualTo(gitProject);
        softly.assertAll();
    }

    /**
     * Tests that the {@link GitProjectAuditLoggingStrategy#execute(JoinPoint)} method throws a
     * {@link NullPointerException} when the provided {@link JoinPoint} is null.
     */
    @Test
    public void testExecute_whenJoinPointIsNull_shouldThrowException() {
        final GitProjectAuditLoggingStrategy strategy =
                new GitProjectAuditLoggingStrategy(new GitProjectArgumentContainer(new GitProject()));
        final JoinPoint joinPoint = null;

        assertThatThrownBy(() -> strategy.execute(joinPoint))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("Cannot invoke \"org.aspectj.lang.JoinPoint.getSignature()\" "
                        + "because \"joinPoint\" is null");
    }
}
