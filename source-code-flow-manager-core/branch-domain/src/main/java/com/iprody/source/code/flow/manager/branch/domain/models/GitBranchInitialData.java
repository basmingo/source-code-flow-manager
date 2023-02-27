package com.iprody.source.code.flow.manager.branch.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

/**
 * Class that represents a set of necessary data for creating and finding a git branch (aka git repository)
 * on remote git-vendor (e.g. Gitlab, Github, etc.) premises.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class GitBranchInitialData {

    /**
     * ID of the project.
     */
    private String projectId;
    /**
     * Path of the project.
     */
    private String projectPath;
    /**
     * Name of the project.
     */
    @NonNull
    private String name;
    /**
     * Ref of the project.
     */
    private String ref;

}
