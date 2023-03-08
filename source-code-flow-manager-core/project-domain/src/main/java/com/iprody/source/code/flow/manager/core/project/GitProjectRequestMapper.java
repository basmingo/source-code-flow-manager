package com.iprody.source.code.flow.manager.core.project;

import reactor.core.publisher.Mono;

/**
 * Represents an operation of mapping on a request object. <p>
 * Mapping transforms implementation of GitProjectRequest
 * into the raw GitProject object for the future handling
 * on the concrete implementation of git-vendor side (Gitlab, Github etc... ).
 *
 * @param <T> should be specified by concrete vendor's  project request class which implements GitProjectRequest
 */
public interface GitProjectRequestMapper<T extends GitProjectRequest> {

    /**
     * Given a Mono of type T, return a Mono of type GitProject.
     *
     * @param request The incoming request.
     * @return A Mono<GitProject>
     */
    Mono<GitProject> map(Mono<T> request);
}
