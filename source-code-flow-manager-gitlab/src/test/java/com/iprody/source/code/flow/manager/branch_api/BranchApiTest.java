package com.iprody.source.code.flow.manager.branch_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.branch_api.entity.Branch;
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

    @BeforeEach
    @SneakyThrows
    void initialize() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWebServer.url("1/repository/branches/testBranch");
        String baseUrl = String.format("http://localhost:%s/",
                mockWebServer.getPort());
        WebClient webClient = WebClient.create(baseUrl);
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
        Branch mockBranch = new Branch("testBranch");
        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(mockBranch))
                .addHeader("Content-Type", "application/json"));

        Mono<Branch> branchMono = branchApi.createBranch(1, "testBranch", "main");

        StepVerifier.create(branchMono)
                .expectNextMatches(branch -> branch.getName().equals("testBranch"))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/1/repository/branches?branch=testBranch&ref=main",
                recordedRequest.getPath());
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfBranchAlreadyExists_soResponseCodeIs400() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .setBody(String.valueOf(HttpStatus.BAD_REQUEST)));

        Mono<Branch> branchMono = branchApi.createBranch(1, "testBranch", "main");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create branch] execution. Response: " +
                        HttpStatus.BAD_REQUEST)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfWrongAuthToken_soResponseCodeIs401() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody(String.valueOf(HttpStatus.UNAUTHORIZED)));

        Mono<Branch> branchMono = branchApi.createBranch(1, "testBranch", "main");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create branch] execution. Response: " +
                        HttpStatus.UNAUTHORIZED)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfForbiddenAction_soResponseCodeIs403() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(String.valueOf(HttpStatus.FORBIDDEN)));

        Mono<Branch> branchMono = branchApi.createBranch(1, "testBranch", "main");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create branch] execution. Response: " +
                        HttpStatus.FORBIDDEN)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfWrongProjectIdOrRefBranchNameOrUrl_soResponseCodeIs404() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        Mono<Branch> branchMono = branchApi.createBranch(1, "testBranch", "main");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create branch] execution. Response: " +
                        HttpStatus.NOT_FOUND)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldNotCreateBranch_becauseOfServerError_soResponseCodeIs500() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));

        Mono<Branch> branchMono = branchApi.createBranch(1, "testBranch", "main");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[create branch] execution. Response: " +
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldReturnBranch_byProjectIdAndBranchName_soResponseIsTheBranch() {
        Branch mockBranch = new Branch("testBranch");
        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(mockBranch))
                .addHeader("Content-Type", "application/json"));

        Mono<Branch> branchMono = branchApi.getBranchByName(1, "testBranch");

        StepVerifier.create(branchMono)
                .expectNextMatches(branch -> branch.getName().equals("testBranch"))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals("/1/repository/branches/testBranch", recordedRequest.getPath());
    }

    @Test
    @SneakyThrows
    void shouldNotReturnBranch_becauseOfWrongAuthToken_soResponseCodeIs401() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody(String.valueOf(HttpStatus.UNAUTHORIZED)));

        Mono<Branch> branchMono = branchApi.getBranchByName(1, "testBranch");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[get branch by name] execution. Response: " +
                        HttpStatus.UNAUTHORIZED)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldNotReturnBranch_becauseOfWrongProjectIdOrBranchNameOrUrl_soResponseCodeIs404() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        Mono<Branch> branchMono = branchApi.getBranchByName(1, "testBranch");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[get branch by name] execution. Response: " +
                        HttpStatus.NOT_FOUND)
                .verify(Duration.ofSeconds(3));
    }

    @Test
    @SneakyThrows
    void shouldNotReturnBranch_becauseOfWrongUrl_soResponseCodeIs500() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));

        Mono<Branch> branchMono = branchApi.getBranchByName(1, "testBranch");

        StepVerifier.create(branchMono)
                .expectErrorMessage("Something went wrong during gitlab operation " +
                        "[get branch by name] execution. Response: " +
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .verify(Duration.ofSeconds(3));
    }
}