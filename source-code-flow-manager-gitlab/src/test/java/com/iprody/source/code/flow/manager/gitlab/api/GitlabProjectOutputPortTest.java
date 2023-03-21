package com.iprody.source.code.flow.manager.gitlab.api;

import com.iprody.source.code.flow.manager.core.project.GitProject;
import com.iprody.source.code.flow.manager.gitlab.api.project.GitlabProjectOutputPort;
import com.iprody.source.code.flow.manager.gitlab.api.project.Project;
import com.iprody.source.code.flow.manager.gitlab.api.project.ProjectApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitlabProjectOutputPortTest {

    private final String errorMessage = "Error";
    private final String projectName = "Name";
    private final int projectId = 23;
    private final String gitProjectName = "Java Job";
    private final String readMeUrl = "readMeURL";
    private final String repoUrl = "repoURL";

    @Mock
    private ProjectApi mockedProjectApi;

    private GitlabProjectOutputPort gitlabProjectOutputPort;


    @BeforeEach
    void setUp() {
        gitlabProjectOutputPort = new GitlabProjectOutputPort(mockedProjectApi);
    }

    @Test
    void shouldCreateNewProjectSuccessFully() {
        final Project mockedProject = new Project();
        mockedProject.setName(projectName);
        mockedProject.setId(projectId);
        mockedProject.setCreatedAt(Instant.now().toString());
        mockedProject.setReadmeUrl(readMeUrl);
        mockedProject.setHttpUrlToRepo(repoUrl);
        when(mockedProjectApi.createProject(any()))
                .thenReturn(Mono.just(mockedProject));

        final Mono<GitProject> projectMono = gitlabProjectOutputPort.createProject(getGitProject());

        StepVerifier.create(projectMono)
                .expectNextMatches(
                        gitProject ->
                                gitProject.getName().equals(mockedProject.getName())
                && gitProject.getId() == mockedProject.getId())
                .verifyComplete();
    }

    @Test
    void shouldThrowGitlabException_becauseResponseError() {
        when(mockedProjectApi.createProject(any()))
                .thenThrow(new GitlabApiException(errorMessage));

        final Mono<GitProject> projectMono = gitlabProjectOutputPort.createProject(getGitProject());

        StepVerifier.create(projectMono)
                .verifyErrorSatisfies(th ->
                        assertThat(th)
                                .isExactlyInstanceOf(GitlabApiException.class)
                                .hasMessage(errorMessage)
                );
    }

    private Mono<GitProject> getGitProject() {
        final GitProject gitProject = new GitProject();
        gitProject.setName(gitProjectName);
        gitProject.setId(projectId);
        gitProject.setReadmeUrl(URI.create(readMeUrl));
        return Mono.just(gitProject);
    }
}
