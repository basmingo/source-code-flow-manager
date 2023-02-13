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

    @BeforeEach
    @SneakyThrows
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/").url().toString();
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("PRIVATE-TOKEN", "token")
                .build();

        projectApiClient = new ProjectApiClient(client);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        mockWebServer.shutdown();
    }

    @Test
    @SneakyThrows
    void shouldCreateNewProject_onBehalfOfUser_thatEncodedInPrivateToken() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(new ObjectMapper().writeValueAsString(getTestProjectRequest()))
        );

        Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());
        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        StepVerifier.create(projectMono)
                .expectNextMatches(project -> project.getName().equals("testProject"))
                .verifyComplete();

        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/projects", recordedRequest.getPath());
        Assertions.assertEquals("token", recordedRequest.getHeader("PRIVATE-TOKEN"));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseTheProjectAlreadyExists_soResponseIs400() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(String.valueOf(HttpStatus.BAD_REQUEST))
        );

        Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create project] execution. Response: " + HttpStatus.BAD_REQUEST)
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseBadRequest_soResponseIs400() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(String.valueOf(HttpStatus.BAD_REQUEST))
        );

        Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create project] execution. Response: " + HttpStatus.BAD_REQUEST)
                .verify();
    }


    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseOfWrongAuthToken_soResponseIs401() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(401)
                        .setBody(String.valueOf(HttpStatus.UNAUTHORIZED))
        );

        Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create project] execution. Response: " + HttpStatus.UNAUTHORIZED)
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotCreateNewProject_becauseInternalServerError_soResponseIs500() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
        );

        Mono<Project> projectMono = projectApiClient.createProject(getTestProjectRequest());

        StepVerifier.create(projectMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create project] execution. Response: " + HttpStatus.INTERNAL_SERVER_ERROR)
                .verify();
    }

    private ProjectCreationRequest getTestProjectRequest() {
        ProjectCreationRequest request = new ProjectCreationRequest();
        request.setName("testProject");
        request.setVisibility("private");
        request.setNamespaceId(13000000);
        return request;
    }
}