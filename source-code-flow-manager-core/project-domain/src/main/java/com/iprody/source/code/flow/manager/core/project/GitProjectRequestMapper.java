package com.iprody.source.code.flow.manager.core.project;

import reactor.core.publisher.Mono;

/**
 * Represents an operation of mapping on a request object. <p>
 * Mapping transforms implementation of GitProjectRequest
 * into the raw GitProject object for the future handling
 * on the concrete implementation of git-vendor side (Gitlab, Github etc... ).
 */
public interface GitProjectRequestMapper {

    /**
     * @param request represents a Mono, which should contain
     *                an instance of an object, that extends abstract GitProjectRequest.
     * @param <T>     Parameter, that represents a name of class, which should extend
     *                GitProjectRequest.
     * @return a Mono, which contains a raw GitProject for the future handling.
     */
    <T extends GitProjectRequest> Mono<GitProject> map(Mono<T> request);
}
