package com.iprody.source.code.flow.manager.core.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

/**
 * Class represents a set of necessary data for creating
 * a git project (aka git repository) on remote git-vendor
 * (e.g. Gitlab, Github, etc.) premises.
 */
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class GitProjectInitialData {
    /**
     * Name of a new project.
     */
    @NonNull
    private String name;

    /**
     * Namespace of a new project (e.g. organisation, group, team, etc.)
     * provides one place to organize related projects.
     */
    private String namespace;

    /**
     * Enable visibility of a new project.
     */
    private boolean visibility;

    /**
     * Base branch of a new project.
     */
    private String baseBranch;

    /**
     * Enable Large File Storage for a new project.
     */
    private boolean isLfsEnabled;

    /**
     * Enable Issues for a new project.
     */
    private boolean isIssuesEnabled;
}
