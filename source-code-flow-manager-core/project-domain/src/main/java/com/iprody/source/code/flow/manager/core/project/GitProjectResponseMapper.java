package com.iprody.source.code.flow.manager.core.project;

import reactor.core.publisher.Mono;

/**
 * Represents an operation of mapping on GitProject. <p>
 * Mapping transforms handled by concrete implementation of
 * git-vendor (Gitlab, Github etc...)
 * into the abstract GitProjectResponse object.
 */
public interface GitProjectResponseMapper {

    /**
     * @param gitProject represents GitProject, which already
     *                   handled by specific Git vendor (Gitlab, Github etc...)
     * @param <T>        Parameter that represents a name of class, which should extend
     *                   GitProjectResponse.
     * @return a Mono, which contains an abstract GitProjectResponse object.
     */
    <T extends GitProjectResponse> Mono<T> map(Mono<GitProject> gitProject);
}
