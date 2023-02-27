package com.iprody.source.code.flow.manager.branch.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.net.URI;

/**
 * Class represents a git project and its vital parts in a vendor related implementation (e.g. Gitlab, Github).
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class GitBranch {

    /**
     * ID of the branch.
     */
    private long id;
    /**
     * Name of the branch.
     */
    @NonNull
    private String name;
    /**
     * Enable merge for the branch.
     */
    private boolean isMerged;
    /**
     * The path where the branch is located.
     */
    private URI webUrl;

}
