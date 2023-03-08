package com.iprody.source.code.flow.manager.core.project;

import reactor.core.publisher.Mono;

/**
 * Represents an operation of mapping on GitProject. <p>
 * Mapping transforms handled by concrete implementation of
 * git-vendor (Gitlab, Github etc...)
 * into the abstract GitProjectResponse object.
 *
 * @param <T> should be specified by concrete vendor's project response class which implements GitProjectResponse
 */
public interface GitProjectResponseMapper<T extends GitProjectResponse> {

    /**
     * @param gitProject represents GitProject, which already
     *                   handled by specific Git vendor (Gitlab, Github etc...)
     * @return a Mono, which contains an abstract GitProjectResponse object.
     */
    Mono<T> map(Mono<GitProject> gitProject);
}
