package com.iprody.source.code.manager.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.manager.client.client.ProjectApiClient;
import com.iprody.source.code.manager.client.entity.project.Project;
import com.iprody.source.code.manager.client.payload.request.ProjectCreationRequest;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ProjectApiClientTest {

    private MockWebServer mockWebServer;

    private ProjectApiClient projectApiClient;

    private final String headerToken = "PRIVATE-TOKEN";

    private final String token = "token";

    private final String projectName = "testProject";

    private final String errorMessageCreateProject = "Something went wrong during gitlab operation "
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

        projectApiClient = new ProjectApiClient(client);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        mockWebServer.shutdown();
    }

    @Disabled
    @Test
    @SneakyThrows
    void shouldCreateNewProject_onBehalfOfUser_thatEncodedInPrivateToken() {
        final int status = 200;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(new ObjectMapper().writeValueAsString(getTestProjectRequest()))
        );

        final Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());
        final RecordedRequest recordedRequest = mockWebServer.takeRequest();

        StepVerifier.create(projectMono)
                .expectNextMatches(project -> project.getName().equals(projectName))
                .verifyComplete();

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

        final Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

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

        final Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

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

        final Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

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

        final Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

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
