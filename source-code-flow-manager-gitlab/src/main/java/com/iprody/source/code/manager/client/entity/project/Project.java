package com.iprody.source.code.manager.client.entity.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Project {

    private int id;
    private String description;
    private String name;
    @JsonProperty("name_with_namespace;")
    private String nameWithNamespace;
    private String path;
    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("ssh_url_to_repo")
    private String sshUrlToRepo;
    @JsonProperty("http_url_to_repo")
    private String httpUrlToRepo;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("readme_url")
    private String readmeUrl;
    @JsonProperty("forks_count")
    private int forksCount;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("star_count")
    private String starCount;
    @JsonProperty("last_activity_at")
    private String lastActivityAt;
}