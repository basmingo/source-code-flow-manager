package com.iprody.source.code.flow.manager.pipeline_job_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.pipeline_job_api.domain.Job;
import com.iprody.source.code.manager.client.payload.exception.GitLabApiException;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

class PipelineJobAPITest {

    private static MockWebServer mockBackEnd;

    private ObjectMapper objectMapper;

    private PipelineJobAPI jobAPI;

    private final Integer projectId = 1;

    private final Integer pipelineId = 1;

    private final String jobsPath = "/{projectId}/pipelines/{pipelineId}/jobs";

    private final String headerContentType = "Content-Type";

    private final String contentType = "application/json";

    private final String errorMessage = "Error during executing pipeline jobs of project: ";

    @BeforeEach
    @SneakyThrows
    void setUpAll() {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();

        final URI uriApi = new DefaultUriBuilderFactory()
                .builder()
                .path(jobsPath)
                .build(projectId, pipelineId);

        mockBackEnd.url(uriApi.getPath());

        objectMapper = new ObjectMapper();

        final String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());

        final WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        jobAPI = new PipelineJobAPI(client);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        mockBackEnd.shutdown();
    }

    public List<Job> data() {
        final Job mockJob1 = new Job();
        mockJob1.setName("Job1: build");

        final Job mockJob2 = new Job();
        mockJob2.setName("Job2: test");

        final Job mockJob3 = new Job();
        mockJob2.setName("Job3: deploy");

        return Arrays.asList(mockJob1, mockJob2, mockJob3);
    }

    @Test
    @SneakyThrows
    void whenGetAllPipelineJobs_thenCorrectPipelineJobs() {
        final List<Job> testJobs = data();

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(testJobs))
                .addHeader(headerContentType, contentType));

        final Flux<Job> pipelineJobs = jobAPI.getPipelineJobs(projectId, pipelineId);

        StepVerifier.create(pipelineJobs)
                .thenConsumeWhile(testJobs::contains)
                .verifyComplete();

        final RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        final URI uriToApi = new DefaultUriBuilderFactory()
                .builder()
                .path(jobsPath)
                .build(projectId, pipelineId);

        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals(uriToApi.getPath(), recordedRequest.getPath());
    }

    @Test
    @SneakyThrows
    void whenGetAllPipelineJobs_thenEmptyPipelineJobs() {
        mockBackEnd.enqueue(new MockResponse()
                .addHeader(headerContentType, contentType));

        final Flux<Job> emptyPipelineJobs = Flux.empty();

        StepVerifier.create(emptyPipelineJobs)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("shouldThrowGitLabApiException_whenGettingAllAvailableJobs_"
            + "butRequestCannotBeAuthorized_viaPassedPrivateTokenAsHeader")
    void throwException_whenGettingJobs() {
        final int status = 401;

        mockBackEnd.enqueue(new MockResponse().setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.UNAUTHORIZED)));

        final Flux<Job> jobsByApi = jobAPI.getPipelineJobs(pipelineId, pipelineId);

        StepVerifier.create(jobsByApi)
                .expectError(GitLabApiException.class)
                .verify();
    }

    @Test
    void shouldThrowPGitLabApiException_whenGettingAllAvailableJobs_butGettingNotFound_viaPassedWrongProjectId() {
        final int status = 404;

        mockBackEnd.enqueue(new MockResponse().setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        final Integer notExistPipelineId = 2;

        final Flux<Job> jobsByApi = jobAPI.getPipelineJobs(projectId, notExistPipelineId);

        StepVerifier.create(jobsByApi).verifyErrorMatches(
                e -> e instanceof GitLabApiException
                        && e.getMessage().equals(errorMessage + projectId));
    }

    @Test
    void shouldThrowPGitLabApiException_whenGettingAllAvailableJobs_butGettingNotFound_viaPassedWrongPipelineId() {
        final int status = 404;

        mockBackEnd.enqueue(new MockResponse().setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        final Integer notExistProjectId = 2;

        final Flux<Job> jobsByApi = jobAPI.getPipelineJobs(notExistProjectId, pipelineId);

        StepVerifier.create(jobsByApi).verifyErrorMatches(
                e -> e instanceof GitLabApiException
                        && e.getMessage().equals(errorMessage + notExistProjectId));

    }

    @Test
    void shouldNotReturnPipelineJobs_becauseOfWrongUrl_soResponseCodeIs500() {
        final int status = 500;

        mockBackEnd.enqueue(new MockResponse().setResponseCode(status)
                .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));

        final Flux<Job> jobsByApi = jobAPI.getPipelineJobs(projectId, pipelineId);

        StepVerifier.create(jobsByApi).verifyErrorMatches(
                e -> e instanceof GitLabApiException
                        && e.getMessage().equals(errorMessage + projectId));
    }
}
