package com.iprody.source.code.flow.manager.core.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Represents a set of operations with a Project object.
 * <p>
 * Should be specified by parameters T, V. that should be
 * extended by specific names of classes, which represents
 * a git-vendor implementation (Gitlab, Github etc... ).
 *
 * @param <T> should be specified by concrete vendor's project request class which implements GitProjectRequest
 * @param <R> should be specified by concrete vendor's project response class which implements GitProjectResponse
 */
@Service
@RequiredArgsConstructor
public final class ProjectEngine<T extends GitProjectRequest, R extends GitProjectResponse>  {

    /**
     * Abstract request mapper, which should be implemented
     *  by a specific object, attached to the git-vendor.
     */
    private final GitProjectRequestMapper<T> requestMapper;

    /**
     * Abstract response mapper, which should be implemented
     *  by a specific object, attached to the git-vendor.
     */
    private final GitProjectResponseMapper<R> responseMapper;

    /**
     * Abstract output port, which should be implemented
     * by specific object, attached to the git-vendor.
     */
    private final GitProjectOutputPort gitProjectOutputPort;

    /**
     * Creates a new git project using data from a specific request.
     *
     * @param request an object which extends GitProjectRequest.
     * @return a Mono containing a response, which extends GitProjectResponse.
     */
    public Mono<R> createProject(T request) {
        return requestMapper
                .map(Mono.just(request))
                .transform(gitProjectOutputPort::createProject)
                .transform(responseMapper::map);
    }
}
