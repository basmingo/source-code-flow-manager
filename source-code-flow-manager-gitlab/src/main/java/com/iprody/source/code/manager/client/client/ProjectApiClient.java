package com.iprody.source.code.manager.client.client;

import com.iprody.source.code.manager.client.entity.project.Project;
import com.iprody.source.code.manager.client.payload.exception.GitLabApiException;
import com.iprody.source.code.manager.client.payload.request.ProjectCreationRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProjectApiClient {
    private final WebClient webClient;

    public ProjectApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Project> createProject(ProjectCreationRequest request) throws GitLabApiException {
        return webClient.post()
                .uri("/projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(
                                new GitLabApiException("Something went wrong during gitlab operation " +
                                        "[create project] execution. Response: " + response.statusCode())))
                .bodyToMono(Project.class);
    }
}
