package com.iprody.source.code.flow.manager.branch.domain.models;

import lombok.*;

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
     * ID of the project
     */
    String projectId;
    /**
     * Path of the project
     */
    String projectPath;
    /**
     * Name of the project
     */
    @NonNull
    String name;
    /**
     * ref of the project
     */
    String ref;

}
