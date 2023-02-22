package com.iprody.source.code.flow.manager.core.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Represents a set of operations with a Project object. <p>
 * Should be specified by parameters T, V. that should be
 * extended by specific names of classes, which represents
 * a git-vendor implementation (Gitlab, Github etc... ).
 */
@Service
@RequiredArgsConstructor
public final class ProjectEngine {

    /**
     * Abstract request mapper, which should be implemented
     *  by a specific object, attached to the git-vendor.
     */
    private final GitProjectRequestMapper requestMapper;

    /**
     * Abstract response mapper, which should be implemented
     *  by a specific object, attached to the git-vendor.
     */
    private final GitProjectResponseMapper responseMapper;

    /**
     * Abstract output port, which should be implemented
     * by specific object, attached to the git-vendor.
     */
    private final GitProjectOutputPort gitProjectOutputPort;

    /**
     * Creates a new git project using data from a specific request.
     *
     * @param request a Mono containing an object which extends GitProjectRequest.
     * @param <T>     represents a name class, that should be an
     *                implementation of GitProjectResponse.
     * @param <R>     represents a name of class, that should be an
     *                implementation of GitProjectRequest.
     * @return a Mono containing a response, which extends GitProjectResponse.
     */
    public <T extends GitProjectRequest, R extends GitProjectResponse> Mono<R> createProject(Mono<T> request) {
        return requestMapper
                .map(request)
                .transform(gitProjectOutputPort::createProject)
                .transform(responseMapper::map);
    }
}
