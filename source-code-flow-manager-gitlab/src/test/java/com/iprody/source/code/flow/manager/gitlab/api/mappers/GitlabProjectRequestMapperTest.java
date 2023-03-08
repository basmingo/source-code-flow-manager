package com.iprody.source.code.flow.manager.gitlab.api.mappers;

import com.iprody.source.code.flow.manager.core.project.GitProject;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectRequest;
import com.iprody.source.code.flow.manager.gitlab.api.project.mappers.GitlabProjectRequestMapper;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SoftAssertionsExtension.class)
class GitlabProjectRequestMapperTest {

    @Test
    void shouldMapAllDataFromRequestToGitProject(SoftAssertions softAssertions) {
        final var testGitlabProjectRequest = getTestGitlabProjectRequest();
        final var requestMapper = new GitlabProjectRequestMapper();
        final Mono<GitProject> testGitProjectMono = requestMapper.map(Mono.just(testGitlabProjectRequest));

        StepVerifier.create(testGitProjectMono)
                .assertNext(gitProject -> {
                    softAssertions.assertThat(gitProject)
                            .returns(testGitlabProjectRequest.getName(), GitProject::getName)
                            .returns(testGitlabProjectRequest.getDescription(), GitProject::getDescription)
                            .returns(testGitlabProjectRequest.getNamespace(), GitProject::getNamespace);
                })
                .verifyComplete();
    }

    private GitlabProjectRequest getTestGitlabProjectRequest() {
        final var testGitlabProjectRequest = new GitlabProjectRequest();
        testGitlabProjectRequest.setName("TestProject");
        testGitlabProjectRequest.setDescription("Test project description");
        testGitlabProjectRequest.setNamespace("iprody/source-code-flow-manager");

        return testGitlabProjectRequest;
    }
}
