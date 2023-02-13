package com.iprody.source.code.manager.client.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreationRequest {

    private String name;
    private String visibility;
    @JsonProperty("namespace_id")
    private int namespaceId;
    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("lfs_enabled")
    private boolean lfsEnabled;
    @JsonProperty("wiki_enabled")
    private boolean wikiEnabled;
    @JsonProperty("issues_enabled")
    private boolean issuesEnabled;
}
