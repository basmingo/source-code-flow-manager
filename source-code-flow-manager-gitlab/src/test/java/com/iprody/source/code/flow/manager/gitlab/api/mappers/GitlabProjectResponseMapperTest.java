package com.iprody.source.code.flow.manager.gitlab.api.mappers;

import com.iprody.source.code.flow.manager.core.project.GitProject;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectResponse;
import com.iprody.source.code.flow.manager.gitlab.api.project.mappers.GitlabProjectResponseMapper;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.time.Instant;

@ExtendWith(SoftAssertionsExtension.class)
class GitlabProjectResponseMapperTest {

    @Test
    void shouldMapAllData_FromGitProject_ToGitlabProjectResponse(SoftAssertions softAssertions) {
        final var testGitProject = getTestGitProject();
        final var responseMapper = new GitlabProjectResponseMapper();
        final var testGitlabProjectResponseMono = responseMapper.map(Mono.just(testGitProject));

        StepVerifier.create(testGitlabProjectResponseMono)
                .assertNext(gitlabProjectResponse -> {
                    softAssertions.assertThat(gitlabProjectResponse)
                            .returns(testGitProject.getId(), GitlabProjectResponse::getId)
                            .returns(testGitProject.getName(), GitlabProjectResponse::getName)
                            .returns(testGitProject.getDescription(), GitlabProjectResponse::getDescription)
                            .returns(testGitProject.getNamespace(), GitlabProjectResponse::getNamespace)
                            .returns(testGitProject.getCreatedAT(), GitlabProjectResponse::getCreatedAt)
                            .returns(testGitProject.isLfsEnabled(), GitlabProjectResponse::isLfsEnabled)
                            .returns(testGitProject.isWikiEnabled(), GitlabProjectResponse::isWikiEnabled)
                            .returns(testGitProject.isCiCdEnabled(), GitlabProjectResponse::isCiCdEnabled)
                            .returns(testGitProject.isContainerRegistryEnabled(),
                                    GitlabProjectResponse::isContainerRegistryEnabled)
                            .returns(testGitProject.isArtifactRegistryEnabled(),
                                    GitlabProjectResponse::isArtifactRegistryEnabled)
                            .returns(testGitProject.getHttpUrlToRepo(), GitlabProjectResponse::getHttpUrlToRepo)
                            .returns(testGitProject.getReadmeUrl(), GitlabProjectResponse::getReadmeUrl);
                })
                .verifyComplete();
    }

    private GitProject getTestGitProject() {
        final var testGitProject = new GitProject();
        testGitProject.setId(1L);
        testGitProject.setName("TestProject");
        testGitProject.setDescription("Test project description");
        testGitProject.setNamespace("iprody/source-code-flow-manager");
        testGitProject.setCreatedAT(Instant.now());
        testGitProject.setLfsEnabled(true);
        testGitProject.setWikiEnabled(true);
        testGitProject.setCiCdEnabled(true);
        testGitProject.setContainerRegistryEnabled(true);
        testGitProject.setArtifactRegistryEnabled(true);
        testGitProject.setHttpUrlToRepo(URI.create("test/repo"));
        testGitProject.setReadmeUrl(URI.create("test/readme"));

        return testGitProject;
    }
}
