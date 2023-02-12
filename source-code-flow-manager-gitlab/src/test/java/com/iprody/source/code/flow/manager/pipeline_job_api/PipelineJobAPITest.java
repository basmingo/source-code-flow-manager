package com.iprody.source.code.flow.manager.pipeline_job_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.pipeline_job_api.domain.Job;
import com.iprody.source.code.manager.client.payload.exception.GitLabApiException;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
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

    @BeforeEach
    @SneakyThrows
    void setUpAll() {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();

        URI uriApi = new DefaultUriBuilderFactory()
                .builder()
                .path("/{projectId}/pipelines/{pipelineId}/jobs")
                .build(projectId, pipelineId);

        mockBackEnd.url(uriApi.getPath());

        objectMapper = new ObjectMapper();

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());

        WebClient client = WebClient.builder()
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
        Job mockJob1 = new Job();
        mockJob1.setName("Job1: build");

        Job mockJob2 = new Job();
        mockJob2.setName("Job2: test");

        Job mockJob3 = new Job();
        mockJob2.setName("Job3: deploy");

        return Arrays.asList(mockJob1, mockJob2, mockJob3);
    }

    @Test
    @SneakyThrows
    void whenGetAllPipelineJobs_thenCorrectPipelineJobs() {
        List<Job> testJobs = data();

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(testJobs))
                .addHeader("Content-Type", "application/json"));

        Flux<Job> pipelineJobs = jobAPI.getPipelineJobs(projectId, pipelineId);

        StepVerifier.create(pipelineJobs)
                .thenConsumeWhile(testJobs::contains)
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        URI uriToApi = new DefaultUriBuilderFactory()
                .builder()
                .path("/{projectId}/pipelines/{pipelineId}/jobs")
                .build(projectId, pipelineId);

        Assertions.assertEquals("GET", recordedRequest.getMethod());
        Assertions.assertEquals(uriToApi.getPath(), recordedRequest.getPath());
    }

    @Test
    @SneakyThrows
    void whenGetAllPipelineJobs_thenEmptyPipelineJobs() {
        mockBackEnd.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json"));

        Flux<Job> emptyPipelineJobs = Flux.empty();

        StepVerifier.create(emptyPipelineJobs)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldThrowGitLabApiException_whenGettingAllAvailableJobs_butRequestCannotBeAuthorized_viaPassedPrivateTokenAsHeader() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(401)
                .setBody(String.valueOf(HttpStatus.UNAUTHORIZED)));

        Flux<Job> jobsByApi = jobAPI.getPipelineJobs(pipelineId, pipelineId);

        StepVerifier.create(jobsByApi)
                .expectError(GitLabApiException.class)
                .verify();
    }

    @Test
    void shouldThrowPGitLabApiException_whenGettingAllAvailableJobs_butGettingNotFound_viaPassedWrongProjectId() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(404)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        Integer notExistPipelineId = 2;

        Flux<Job> jobsByApi = jobAPI.getPipelineJobs(projectId, notExistPipelineId);

        StepVerifier.create(jobsByApi).verifyErrorMatches(
                e -> e instanceof GitLabApiException &&
                        e.getMessage().equals("Error occurred while executing Fetch all available pipeline jobs of Gitlab Project: " + projectId));
    }

    @Test
    void shouldThrowPGitLabApiException_whenGettingAllAvailableJobs_butGettingNotFound_viaPassedWrongPipelineId() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(404)
                .setBody(String.valueOf(HttpStatus.NOT_FOUND)));

        Integer notExistProjectId = 2;

        Flux<Job> jobsByApi = jobAPI.getPipelineJobs(notExistProjectId, pipelineId);

        StepVerifier.create(jobsByApi).verifyErrorMatches(
                e -> e instanceof GitLabApiException &&
                        e.getMessage().equals("Error occurred while executing Fetch all available pipeline jobs of Gitlab Project: " + notExistProjectId));

    }

    @Test
    void shouldNotReturnPipelineJobs_becauseOfWrongUrl_soResponseCodeIs500() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(500)
                .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)));

        Flux<Job> jobsByApi = jobAPI.getPipelineJobs(projectId, pipelineId);

        StepVerifier.create(jobsByApi).verifyErrorMatches(
                e -> e instanceof GitLabApiException &&
                        e.getMessage().equals("Error occurred while executing Fetch all available pipeline jobs of Gitlab Project: " + projectId));
    }
}