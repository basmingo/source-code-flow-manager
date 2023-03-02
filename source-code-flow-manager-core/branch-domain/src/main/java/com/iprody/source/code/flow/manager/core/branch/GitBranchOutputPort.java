package com.iprody.source.code.flow.manager.core.branch;

import reactor.core.publisher.Mono;

/**
 * Interface represents a set of operations for manipulating over a git project (aka git repository)
 * that established remotely on git-vendor (aka Gitlab, Github, etc.) premises.
 */
public interface GitBranchOutputPort {

    /**
     * Creates a new branch.Uses the data from the received object as a parameter.
     *
     * @return GitBranch data of a newly created project or null if GitBranch doesn't exist.
     * @param gitBranchInitialData
     */
    Mono<GitBranch> createBranch(GitBranchInitialData gitBranchInitialData);

    /**
     * Finds a new branch.Uses the data from the received object as a parameter.
     *
     * @return GitBranch data of a newly created project or null if GitBranch doesn't exist.
     * @param gitBranchInitialData
     */
    Mono<GitBranch> findBranch(GitBranchInitialData gitBranchInitialData);

}
