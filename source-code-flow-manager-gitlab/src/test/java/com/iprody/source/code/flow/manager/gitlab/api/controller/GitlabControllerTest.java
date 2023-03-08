package com.iprody.source.code.flow.manager.gitlab.api.controller;

import com.iprody.source.code.flow.manager.core.project.ProjectEngine;
import com.iprody.source.code.flow.manager.gitlab.api.project.controller.GitlabController;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectRequest;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = GitlabControllerTest.GitlabControllerTestConfiguration.class)
@WebFluxTest(controllers = GitlabController.class)
class GitlabControllerTest {

    @ComponentScan(basePackageClasses = GitlabController.class)
    @Configuration
    @MockBean(classes = ProjectEngine.class)
    static class GitlabControllerTestConfiguration {
    }

    private final String baseUrl = "/gitlab/projects";
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ProjectEngine<GitlabProjectRequest, GitlabProjectResponse> mockedProjectEngine;

    @Test
    void shouldCreateGitlabProject_thenReturnGitlabProjectResponse_soResponseCodeIs201() {
        final String testName = "name";
        final String testDescription = "description";
        final String testNamespace = "iprody/source-code-flow-manager";
        final long testOwnerId = 1;
        final long testProjectId = 1;

        final var gitlabProjectRequest = new GitlabProjectRequest(testName,
                testDescription, testNamespace, testOwnerId);
        final var expectedGitlabProjectResponse = new GitlabProjectResponse();
        expectedGitlabProjectResponse.setId(testProjectId);
        expectedGitlabProjectResponse.setName(testName);
        expectedGitlabProjectResponse.setDescription(testDescription);
        expectedGitlabProjectResponse.setNamespace(testNamespace);

        given(mockedProjectEngine.createProject(gitlabProjectRequest))
                .willReturn(Mono.just(expectedGitlabProjectResponse));

        final WebTestClient.ResponseSpec responseToClient = webTestClient.post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(gitlabProjectRequest)
                .exchange();

        responseToClient.expectStatus()
                .isCreated();

        final var testResultGitlabProjectResponseFlux = responseToClient
                .returnResult(GitlabProjectResponse.class).getResponseBody();

        StepVerifier.create(testResultGitlabProjectResponseFlux)
                .assertNext(response -> {
                    assertThat(response).isEqualTo(expectedGitlabProjectResponse);
                })
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("getInvalidGitlabProjectRequest")
    void shouldNotCreateGitlabProject_becauseOfMistakeInRequest_soResponseCodeIs400(GitlabProjectRequest request) {
        final WebTestClient.ResponseSpec response = webTestClient.post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange();

        final String jsonStatus = "$.status";
        final String jsonError = "$.error";
        final String expectedStatusCode = "400";
        final String expectedError = "Bad Request";
        response.expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath(jsonStatus).isEqualTo(expectedStatusCode)
                .jsonPath(jsonError).isEqualTo(expectedError);
    }

    private static Stream<Arguments> getInvalidGitlabProjectRequest() {
        return Stream.of(
                Arguments.of(new GitlabProjectRequest("",
                        "description1", "namespace1", 1)),
                Arguments.of(new GitlabProjectRequest("name2",
                        "", "namespace2", 1)),
                Arguments.of(new GitlabProjectRequest("name3",
                        "description3", "", 1)),
                Arguments.of(new GitlabProjectRequest("name4",
                        "description4", "namespace4", -1)));
    }
}
