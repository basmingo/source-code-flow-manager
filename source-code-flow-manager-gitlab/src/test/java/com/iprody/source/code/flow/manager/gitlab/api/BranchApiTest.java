package com.iprody.source.code.flow.manager.gitlab.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.gitlab.api.branch.Branch;
import com.iprody.source.code.flow.manager.gitlab.api.branch.BranchApi;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

class BranchApiTest {

    private MockWebServer mockWebServer;
    private BranchApi branchApi;

    private final String branchName = "testBranch";

    private final String branchRef = "main";

    private final int projectId = 1;

    private final String errorMessage = "Something went wrong during Gitlab operation ";

    private final String errorMessageCreateBranch = "[create branch] execution. Response: ";

    private final String errorMessageGetBranch = "[get branch by name] execution. Response: ";

    private final String headerContentType = "Content-Type";

    private final String contentType = "application/json";

    private final int duration = 3;

    @BeforeEach
    @SneakyThrows
    void initialize() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWebServer.url("1/repository/branches/testBranch");
        final String baseUrl = String.format("http://localhost:%s/",
                mockWebServer.getPort());
        final WebClient webClient = WebClient.create(baseUrl);
        branchApi = new BranchApi(webClient);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        mockWebServer.shutdown();
    }

    @Test
    @SneakyThrows
    void shouldCreateAndReturnBranch_usingProjectIdAndNewBranchNameAndReferenceBranchName_soResponseIsNewBranch() {
        final Branch mockBranch = new Branch(branchName);
        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(mockBranch))
                .addHeader(headerContentType, contentType));

        final Mono<Branch> branchMono = branchApi.createBranch(projectId, branchName, branchRef);

        StepVerifier.create(branchMono)
                .expectNextMatches(branch -> branch.getName().equals(branchName))
                .verifyComplete();

        final RecordedRequest recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/1/repository/branches?branch=testBranch&ref=main",
                recordedRequest.getPath());
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfBranchAlreadyExists_soResponseCodeIs400() {
        final int status = 400;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.BAD_REQUEST)));

        final Mono<Branch> branchMono = branchApi.createBranch(projectId, branchName, branchRef);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageCreateBranch
                        + HttpStatus.BAD_REQUEST)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfWrongAuthToken_soResponseCodeIs401() {
        final int status = 401;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.UNAUTHORIZED)));

        final Mono<Branch> branchMono = branchApi.createBranch(projectId, branchName, branchRef);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageCreateBranch
                        + HttpStatus.UNAUTHORIZED)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfForbiddenAction_soResponseCodeIs403() {
        final int status = 403;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.FORBIDDEN)));

        final Mono<Branch> branchMono = branchApi.createBranch(projectId, branchName, branchRef);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageCreateBranch
                        + HttpStatus.FORBIDDEN)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfWrongProjectIdOrRefBranchNameOrUrl_soResponseCodeIs404() {
        final int status = 404;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        final Mono<Branch> branchMono = branchApi.createBranch(projectId, branchName, branchRef);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageCreateBranch
                        + HttpStatus.NOT_FOUND)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfServerError_soResponseCodeIs500() {
        final int status = 500;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));

        final Mono<Branch> branchMono = branchApi.createBranch(projectId, branchName, branchRef);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageCreateBranch
                        + HttpStatus.INTERNAL_SERVER_ERROR)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldReturnBranch_byProjectIdAndBranchName_soResponseIsTheBranch() {
        final Branch mockBranch = new Branch(branchName);
        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(mockBranch))
                .addHeader(headerContentType, contentType));

        final Mono<Branch> branchMono = branchApi.getBranchByName(projectId, branchName);

        StepVerifier.create(branchMono)
                .expectNextMatches(branch -> branch.getName().equals(branchName))
                .verifyComplete();

        final RecordedRequest recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals("/1/repository/branches/testBranch", recordedRequest.getPath());
    }

    @Test
    @SneakyThrows
    void shouldNotReturnBranch_becauseOfWrongAuthToken_soResponseCodeIs401() {
        final int status = 401;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.UNAUTHORIZED)));

        final Mono<Branch> branchMono = branchApi.getBranchByName(projectId, branchName);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageGetBranch
                        + HttpStatus.UNAUTHORIZED)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldNotReturnBranch_becauseOfWrongProjectIdOrBranchNameOrUrl_soResponseCodeIs404() {
        final int status = 404;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        final Mono<Branch> branchMono = branchApi.getBranchByName(projectId, branchName);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageGetBranch
                        + HttpStatus.NOT_FOUND)
                .verify(Duration.ofSeconds(duration));
    }

    @Test
    @SneakyThrows
    void shouldNotReturnBranch_becauseOfWrongUrl_soResponseCodeIs500() {
        final int status = 500;

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));

        final Mono<Branch> branchMono = branchApi.getBranchByName(projectId, branchName);

        StepVerifier.create(branchMono)
                .expectErrorMessage(errorMessage + errorMessageGetBranch
                        + HttpStatus.INTERNAL_SERVER_ERROR)
                .verify(Duration.ofSeconds(duration));
    }
}
