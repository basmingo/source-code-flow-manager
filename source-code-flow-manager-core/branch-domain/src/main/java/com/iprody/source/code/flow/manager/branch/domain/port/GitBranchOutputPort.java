package com.iprody.source.code.flow.manager.branch.domain.port;

import com.iprody.source.code.flow.manager.branch.domain.models.GitBranch;
import com.iprody.source.code.flow.manager.branch.domain.models.GitBranchInitialData;
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
     */
    Mono<GitBranch> createBranch(GitBranchInitialData gitBranchInitialData);

    /**
     * Finds a new branch.Uses the data from the received object as a parameter.
     *
     * @return GitBranch data of a newly created project or null if GitBranch doesn't exist.
     */
    Mono<GitBranch> findBranch(GitBranchInitialData gitBranchInitialData);

}
