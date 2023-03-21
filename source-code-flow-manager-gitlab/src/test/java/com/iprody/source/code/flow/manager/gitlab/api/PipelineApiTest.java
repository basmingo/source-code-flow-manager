package com.iprody.source.code.flow.manager.gitlab.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.gitlab.api.pipeline.GitlabPipeline;
import com.iprody.source.code.flow.manager.gitlab.api.pipeline.GitlabPipelineApi;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PipelineApiTest {

    private MockWebServer mockWebServer;
    private GitlabPipelineApi pipeLineClient;

    private final Integer projectId = 2;

    private final Integer pipelineId = 3;

    private final String headerContentType = "Content-Type";

    private final String contentType = "application/json";

    private final String headerPrivateToken = "PRIVATE-TOKEN";

    private final String secretToken = "secretToken";

    private final String errorMessage = "Something went wrong while get pipeline operation";

    @BeforeEach
    @SneakyThrows
    void setUp() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        final String baseUrl = mockWebServer.url("/").toString();
        final WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                .defaultHeader(headerPrivateToken, secretToken)
                .build();
        pipeLineClient = new GitlabPipelineApi(client);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        mockWebServer.shutdown();
    }

    @SneakyThrows
    @Test
    public void shouldReturnCorrectPipeline_usingCorrectRequest_soCorrectGetResponse() {
        final GitlabPipeline mockPipeline = new GitlabPipeline(pipelineId);

        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(new ObjectMapper().writeValueAsString(mockPipeline))
                        .addHeader(headerContentType, contentType)
        );

        final Mono<GitlabPipeline> pipelineMono = pipeLineClient.getPipeline(projectId, pipelineId);

        StepVerifier.create(pipelineMono)
                .expectNextMatches(pipeline -> pipeline.getId() == pipelineId)
                .verifyComplete();

        final RecordedRequest recordedRequest = mockWebServer.takeRequest();

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(recordedRequest)
                .returns("GET", RecordedRequest::getMethod)
                .returns("/2/pipelines/3", RecordedRequest::getPath);

        softAssertions.assertThat(recordedRequest.getHeader(headerPrivateToken)).isEqualTo(secretToken);
    }

    @SneakyThrows
    @Test
    public void shouldNotReturnGitlabPipeline_becauseEmptyPrivateTokenForAuthentication_soResponseCodeIs401() {
        final int status = 401;
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.UNAUTHORIZED))
                        .setHeader(headerPrivateToken, "")
        );

        final Mono<GitlabPipeline> pipelineMono = pipeLineClient.getPipeline(projectId, pipelineId);

        StepVerifier.create(pipelineMono)
                .verifyErrorSatisfies(th ->
                        assertThat(th)
                                .isExactlyInstanceOf(GitlabApiException.class)
                                .hasMessage(errorMessage));
    }

    @SneakyThrows
    @Test
    public void shouldNotReturnGitlabPipeline_becauseMissingPrivateTokenForAuthentication_soResponseCodeIs401() {
        final int status = 401;
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.UNAUTHORIZED))
        );

        final Mono<GitlabPipeline> pipelineMono = pipeLineClient.getPipeline(projectId, pipelineId);

        StepVerifier.create(pipelineMono)
                .verifyErrorSatisfies(th ->
                        assertThat(th)
                                .isExactlyInstanceOf(GitlabApiException.class)
                                .hasMessage(errorMessage));
    }

    @SneakyThrows
    @Test
    public void shouldNotReturnGitlabPipeline_usingInvalidToken_soResponseCodeIs404() {
        final int status = 404;
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.UNAUTHORIZED))
                        .setHeader(headerPrivateToken, "invalidToken")
        );

        final Mono<GitlabPipeline> pipelineMono = pipeLineClient.getPipeline(projectId, pipelineId);

        StepVerifier.create(pipelineMono)
                .verifyErrorSatisfies(th ->
                        assertThat(th)
                                .isExactlyInstanceOf(GitlabApiException.class)
                                .hasMessage(errorMessage));
    }
}
