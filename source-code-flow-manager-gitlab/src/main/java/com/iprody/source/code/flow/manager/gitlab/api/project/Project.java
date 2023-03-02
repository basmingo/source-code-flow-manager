package com.iprody.source.code.flow.manager.gitlab.api.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Class represents a project and its vital parts on inner API side.
 */
@Getter
public class Project {

    /**
     * Id of the project.
     */
    private int id;

    /**
     * Description of the project.
     */
    private String description;

    /**
     * Name of the project.
     */
    private String name;
    /**
     * Name of the project with namespace.
     */
    @JsonProperty("name_with_namespace;")
    private String nameWithNamespace;

    /**
     * Path of the project location.
     */
    private String path;

    /**
     * Path of the project location with namespace.
     */
    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;

    /**
     * Time of project creation.
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * Default branch of project.
     */
    @JsonProperty("default_branch")
    private String defaultBranch;

    /**
     * SSl url of project repository.
     */
    @JsonProperty("ssh_url_to_repo")
    private String sshUrlToRepo;

    /**
     * Http url of project repository.
     */
    @JsonProperty("http_url_to_repo")
    private String httpUrlToRepo;

    /**
     * Web url of the project.
     */
    @JsonProperty("web_url")
    private String webUrl;

    /**
     * Readme url of project.
     */
    @JsonProperty("readme_url")
    private String readmeUrl;

    /**
     * Count of forks.
     */
    @JsonProperty("forks_count")
    private int forksCount;

    /**
     * Avatar url of project.
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     * Count of star of project.
     */
    @JsonProperty("star_count")
    private String starCount;

    /**
     * Time of last activity.
     */
    @JsonProperty("last_activity_at")
    private String lastActivityAt;
}
