package com.iprody.source.code.flow.manager.gitlab.api.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents a project request and its vital parts on inner API side.
 */
@Getter
@Setter
public class ProjectCreationRequest {

    /**
     * Name of project.
     */
    private String name;

    /**
     * Visibility of project.
     */
    private String visibility;

    /**
     * Namespace id.
     */
    @JsonProperty("namespace_id")
    private int namespaceId;

    /**
     * Default branch.
     */
    @JsonProperty("default_branch")
    private String defaultBranch;

    /**
     * Enabled lfs.
     */
    @JsonProperty("lfs_enabled")
    private boolean lfsEnabled;

    /**
     * Enabled wiki.
     */
    @JsonProperty("wiki_enabled")
    private boolean wikiEnabled;

    /**
     * Enable issues.
     */
    @JsonProperty("issues_enabled")
    private boolean issuesEnabled;
}
