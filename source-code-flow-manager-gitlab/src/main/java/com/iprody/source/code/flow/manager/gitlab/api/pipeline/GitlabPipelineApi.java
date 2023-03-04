package com.iprody.source.code.flow.manager.gitlab.api.pipeline;

import com.iprody.source.code.flow.manager.gitlab.exception.GitlabApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitlabPipelineApi {

    /**
     * Configured and injected WebClient.
     */
    private final WebClient webClient;

    /**
     * Constructor for injecting Webclient.
     *
     * @param webClient
     */
    public GitlabPipelineApi(final WebClient webClient) {
        this.webClient = webClient;
    }


    /**
     * Get exists pipeline.
     *
     * @param projectId
     * @param pipelineId
     * @return GitlabPipeline
     */
    public Mono<GitlabPipeline> getPipeline(int projectId, int pipelineId) {
        return webClient
                .get()
                .uri("{projectId}/pipelines/{pipelineId}", projectId, pipelineId)
                .retrieve()
                .bodyToMono(GitlabPipeline.class)
                .onErrorMap(throwable ->
                        new GitlabApiException("Something went wrong while get pipeline operation", throwable));
    }
}
