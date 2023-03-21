package com.iprody.source.code.flow.manager.gitlab.api.pipeline.job;

import com.iprody.source.code.flow.manager.gitlab.api.GitlabApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * The class represents a client side PipelineJob API for interacting with the vendor's API.
 */
@Service
public class PipelineJobApi {

    /**
     * Configured and injected WebClient.
     */
    private final WebClient webClient;

    /**
     * Exception message.
     */
    private final String exceptionMessage = "Error during executing pipeline jobs of project: ";

    /**
     * Constructor for inject WebClient.
     *
     * @param webClient injecting configured webclient.
     */
    public PipelineJobApi(final WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Get exists pipeline jobs.
     *
     * @param projectId - project id.
     * @param pipelineId - pipeline id.
     * @return Job.
     */
    public Flux<Job> getPipelineJobs(Integer projectId, Integer pipelineId) {
        return webClient.get()
                .uri("{projectId}/pipelines/{pipelineId}/jobs", projectId, pipelineId)
                .retrieve()
                .bodyToFlux(Job.class)
                .onErrorMap(throwable -> new GitlabApiException(exceptionMessage + projectId, throwable));
    }
}
