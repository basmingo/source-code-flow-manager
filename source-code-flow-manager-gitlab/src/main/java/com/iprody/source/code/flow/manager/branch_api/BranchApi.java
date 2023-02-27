package com.iprody.source.code.flow.manager.branch_api;

import com.iprody.source.code.flow.manager.GitLabApiException;
import com.iprody.source.code.flow.manager.branch_api.entity.Branch;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The class represents a client side Branch API for interacting with the vendor's API.
 */
@Service
public class BranchApi {
    /**
     * Configured and injected WebClient.
     */
    private final WebClient webClient;

    /**
     * Repeating part of message exception.
     */
    private final String exceptionMessage = "Something went wrong during gitlab operation ";

    /**
     * Constructor for inject WebClient.
     *
     * @param webClient injecting configured webclient.
     */
    public BranchApi(final WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Create new branch for exists project with 'id'.
     *
     * @param id     - project id.
     * @param branch - branch name.
     * @param ref    - branch url.
     * @return Branch.
     */
    public Mono<Branch> createBranch(int id, String branch, String ref) throws GitLabApiException {
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
                                new GitLabApiException(exceptionMessage
                                        + "[create branch] execution. Response: "
                                        + response.statusCode())))
                .bodyToMono(Branch.class);
    }

    /**
     * Get exists branch by name.
     *
     * @param id     - project id.
     * @param branch - branch name.
     * @return Branch.
     */
    public Mono<Branch> getBranchByName(int id, String branch) throws GitLabApiException {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("{id}/repository/branches/{name}")
                        .build(id, branch))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(
                                new GitLabApiException(exceptionMessage
                                        + "[get branch by name] execution. Response: "
                                        + response.statusCode())))
                .bodyToMono(Branch.class);
    }
}
