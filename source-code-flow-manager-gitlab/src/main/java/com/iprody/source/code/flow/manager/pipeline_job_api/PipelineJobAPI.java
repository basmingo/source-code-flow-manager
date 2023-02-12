package com.iprody.source.code.flow.manager.pipeline_job_api;

import com.iprody.source.code.flow.manager.pipeline_job_api.domain.Job;
import com.iprody.source.code.manager.client.payload.exception.GitLabApiException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class PipelineJobAPI {

    private final WebClient webClient;

    public PipelineJobAPI(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Job> getPipelineJobs(Integer projectId, Integer pipelineId) throws GitLabApiException {
        return webClient.get()
                .uri("{projectId}/pipelines/{pipelineId}/jobs", projectId, pipelineId)
                .retrieve()
                .bodyToFlux(Job.class)
                .onErrorMap(throwable -> new GitLabApiException("Error occurred while executing Fetch all available pipeline jobs of Gitlab Project: " + projectId, throwable));
    }
}
