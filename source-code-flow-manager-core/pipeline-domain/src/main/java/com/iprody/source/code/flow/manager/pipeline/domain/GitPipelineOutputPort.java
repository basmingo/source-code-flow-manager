package com.iprody.source.code.flow.manager.pipeline.domain;

import reactor.core.publisher.Mono;

/**
 * Interface represents a set of operations for manipulating over a git pipeline
 * that established remotely on git-vendor (aka Gitlab, Github, etc.) premises.
 */
public interface GitPipelineOutputPort {
    /**
     * Finds last GitPipeline for a Project, uses data from the received Project as a parameter.
     * <p>
     *
     * @param gitProject project that should contain needed GitPipeline
     * @return Mono containing GitPipeline which should relate to the Project in a remote repository
     * <p> if GitPipeline doesn't exist for the Project, returns empty Mono
     */
    Mono<GitPipeline> getPipeline(GitProject gitProject);
}
