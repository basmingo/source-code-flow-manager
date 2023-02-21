package com.iprody.source.code.flow.manager.branch_api;

import com.iprody.source.code.flow.manager.GitLabApiException;
import com.iprody.source.code.flow.manager.branch_api.entity.Branch;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BranchApi {

    public WebClient webClient;

    public BranchApi(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Branch> createBranch(int id, String branch, String ref)  throws GitLabApiException {
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("{id}/repository/branches")
                        .queryParam("branch", branch)
                        .queryParam("ref", ref)
                        .build(id))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(
                                new GitLabApiException("Something went wrong during gitlab operation " +
                                        "[create branch] execution. Response: " + response.statusCode())))
                .bodyToMono(Branch.class);
    }

    public Mono<Branch> getBranchByName(int id, String branch) throws GitLabApiException {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("{id}/repository/branches/{name}")
                        .build(id, branch))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(
                                new GitLabApiException("Something went wrong during gitlab operation " +
                                        "[get branch by name] execution. Response: " + response.statusCode())))
                .bodyToMono(Branch.class);
    }
}