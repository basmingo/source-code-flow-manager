package com.iprody.source.code.flow.manager.core.project;

import reactor.core.publisher.Mono;

/**
 * Interface represents a set of operations for manipulating over
 * a git project (aka git repository) that established remotely
 * on git-vendor (aka Gitlab, Github, etc.) premises.
 */
public interface GitProjectOutputPort {
    /**
     * Creates a new project using data from the received GitProjectInitialData as a parameter.
     *
     * @param gitProject consists of configured data to create a new project in a remote repository
     * @return GitProject relates to create a new project in a remote repository
     */
    Mono<GitProject> createProject(Mono<GitProject> gitProject);
}
