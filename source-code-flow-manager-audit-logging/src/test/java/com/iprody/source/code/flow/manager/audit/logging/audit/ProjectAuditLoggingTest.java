package com.iprody.source.code.flow.manager.audit.logging.audit;

import com.iprody.source.code.flow.manager.audit.logging.AspectConfig;
import com.iprody.source.code.flow.manager.audit.logging.audit.git.GitStrategyMappingRegistry;
import com.iprody.source.code.flow.manager.data.access.entity.GitAdministrator;
import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import com.iprody.source.code.flow.manager.data.access.entity.GitProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {TestClassForProjectAuditLogging.class, ProjectAuditLoggingAspect.class, AspectConfig.class})
@MockBean(classes = {ProjectLogRepository.class, GitStrategyMappingRegistry.class})
public class ProjectAuditLoggingTest {
    @Autowired
    private TestClassForProjectAuditLogging testClass;
    @Autowired
    private GitStrategyMappingRegistry registry;
    @SpyBean
    private ProjectAuditLoggingAspect projectAuditLoggingAspect;
    @MockBean
    private GitStrategyMappingContext mockMappingContext;


    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedMethodAfterExecution() {
        Mockito.when(registry.lookup(Mockito.any())).thenReturn(mockMappingContext);

        testClass.methodWithProjectLogOperationAnnotation(getMockGitProject());

        verify(projectAuditLoggingAspect, times(1))
                .saveAfterExecutionAdvice(Mockito.any(), Mockito.any());
        verify(projectAuditLoggingAspect, never())
                .afterThrowingException(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedMethodWithMultipleArgsAfterExecution() {
        Mockito.when(registry.lookup(Mockito.any())).thenReturn(mockMappingContext);

        testClass.methodWithProjectLogOperationAnnotation(getMockGitProject(), "testValue");

        verify(projectAuditLoggingAspect, times(1))
                .saveAfterExecutionAdvice(Mockito.any(), Mockito.any());
        verify(projectAuditLoggingAspect, never())
                .afterThrowingException(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldNotInvokeLoggingAspectsOnAnnotatedMethodWithoutArgsAfterExecution() {
        testClass.methodWithProjectLogOperationAnnotation();

        verify(projectAuditLoggingAspect, never())
                .saveAfterExecutionAdvice(Mockito.any(), Mockito.any());
        verify(projectAuditLoggingAspect, never())
                .afterThrowingException(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldInvokeLoggingAspectsOnAnnotatedAfterThrowExecution() {
        Mockito.when(registry.lookup(Mockito.any())).thenReturn(mockMappingContext);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> testClass.methodWithProjectLogOperationAnnotationThrowException(getMockGitProject()))
                .withMessage("Something went wrong");

        verify(projectAuditLoggingAspect, times(1))
                .saveAfterExecutionAdvice(Mockito.any(), Mockito.any());
        verify(projectAuditLoggingAspect, times(1))
                .afterThrowingException(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldNotInvokeLoggingAspectsOnAnnotatedAfterExecution() {
        Mockito.when(registry.lookup(Mockito.any()))
                .thenThrow(new IllegalArgumentException("No Strategy found for type: " + Object.class));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> testClass.methodWithProjectLogOperationAnnotation(new Object()))
                .withMessage("No Strategy found for type: class java.lang.Object");

        verify(projectAuditLoggingAspect, times(1))
                .saveAfterExecutionAdvice(Mockito.any(), Mockito.any());
        verify(projectAuditLoggingAspect, never())
                .afterThrowingException(Mockito.any(), Mockito.any(), Mockito.any());
    }

    private GitProject getMockGitProject() {
        final GitProvider gitProvider = new GitProvider();
        final long id = 1;
        gitProvider.setId(id);
        final GitAdministrator gitAdministrator = new GitAdministrator();
        gitAdministrator.setId(id);

        final GitProject gitProject = new GitProject();
        gitProject.setId(id);
        gitProject.setGitAdministrator(gitAdministrator);
        gitProject.setGitProvider(gitProvider);

        return gitProject;
    }
}
