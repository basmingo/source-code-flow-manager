package com.iprody.source.code.flow.manager.gitlab.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.gitlab.api.project.Project;
import com.iprody.source.code.flow.manager.gitlab.api.project.ProjectApi;
import com.iprody.source.code.flow.manager.gitlab.api.project.ProjectCreationRequest;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ProjectApiTest {

    private MockWebServer mockWebServer;

    private ProjectApi projectApi;

    private final String headerToken = "PRIVATE-TOKEN";

    private final String token = "token";

    private final String projectName = "testProject";

    private final String errorMessageCreateProject = "Something went wrong during Gitlab operation "
            + "[create project] execution. Response: ";

    @BeforeEach
    @SneakyThrows
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final String baseUrl = mockWebServer.url("/").url().toString();
        final WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(headerToken, token)
                .build();

        projectApi = new ProjectApi(client);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        mockWebServer.shutdown();
    }

    @Test
    @SneakyThrows
    void shouldCreateNewProject_onBehalfOfUser_thatEncodedInPrivateToken() {
        final int status = 200;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(new ObjectMapper().writeValueAsString(getTestProjectRequest()))
        );

        final Mono<Project> projectMono = projectApi.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectNextMatches(project -> project.getName().equals(projectName))
                .verifyComplete();

        final RecordedRequest recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/projects", recordedRequest.getPath());
        Assertions.assertEquals(token, recordedRequest.getHeader(headerToken));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseTheProjectAlreadyExists_soResponseIs400() {
        final int status = 400;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(String.valueOf(HttpStatus.BAD_REQUEST))
        );

        final Mono<Project> projectMono = projectApi.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage(errorMessageCreateProject + HttpStatus.BAD_REQUEST)
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseBadRequest_soResponseIs400() {
        final int status = 400;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(String.valueOf(HttpStatus.BAD_REQUEST))
        );

        final Mono<Project> projectMono = projectApi.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage(errorMessageCreateProject + HttpStatus.BAD_REQUEST)
                .verify();
    }


    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseOfWrongAuthToken_soResponseIs401() {
        final int status = 401;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.UNAUTHORIZED))
        );

        final Mono<Project> projectMono = projectApi.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage(errorMessageCreateProject + HttpStatus.UNAUTHORIZED)
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseInternalServerError_soResponseIs500() {
        final int status = 500;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
        );

        final Mono<Project> projectMono = projectApi.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage(errorMessageCreateProject + HttpStatus.INTERNAL_SERVER_ERROR)
                .verify();
    }

    private ProjectCreationRequest getTestProjectRequest() {
        final int namespaceId = 13000000;

        final ProjectCreationRequest request = new ProjectCreationRequest();
        request.setName(projectName);
        request.setVisibility("private");
        request.setNamespaceId(namespaceId);
        return request;
    }
}
