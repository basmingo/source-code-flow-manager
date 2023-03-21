package com.iprody.source.code.flow.manager.gitlab.api.project;

import com.iprody.source.code.flow.manager.gitlab.api.GitlabApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The class represents a client side project API for interacting with the vendor's API.
 */
@Service
public class ProjectApi {

    /**
     * Configured and injected WebClient.
     */
    private final WebClient webClient;

    /**
     * Constructor for inject WebClient.
     *
     * @param webClient injecting configured webclient.
     */
    public ProjectApi(final WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Create new project.
     *
     * @param request - creation params.
     * @return Project.
     */
    public Mono<Project> createProject(ProjectCreationRequest request) {
        return webClient.post()
                .uri("/projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(
                                new GitlabApiException("Something went wrong during Gitlab operation "
                                        + "[create project] execution. Response: " + response.statusCode())))
                .bodyToMono(Project.class);
    }
}
