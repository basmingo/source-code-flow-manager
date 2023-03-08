package com.iprody.source.code.flow.manager.gitlab.api.project.dto;

import com.iprody.source.code.flow.manager.core.project.GitProjectResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.time.Instant;

/**
 * It's a DTO response that contains the data of the GitProject in Gitlab.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Initial data to create a project")
public class GitlabProjectResponse implements GitProjectResponse {
    /**
     * ID of the project.
     */
    private long id;
    /**
     * Name of the project.
     */
    private String name;
    /**
     * Description of the project.
     */
    private String description;
    /**
     * Namespace of the project.
     * It provides one place in Gitlab to organize some related to each other projects.
     */
    private String namespace;
    /**
     * Time of the project creation.
     */
    private Instant createdAt;
    /**
     * OwnerId of the project.
     * It is stored in the GitAdministrator repository.
     */
    private long ownerId;
    /**
     * Enable Large File Storage (LFS) fot the project.
     */
    private boolean lfsEnabled;
    /**
     * Enable Wiki pages for the project.
     */
    private boolean wikiEnabled;
    /**
     * Enable CI/CD for the project.
     */
    private boolean ciCdEnabled;
    /**
     * Enable of a container registry on git-vendor side
     * to store container images for the project.
     */
    private boolean containerRegistryEnabled;
    /**
     * Enable artifact registry for the project.
     */
    private boolean artifactRegistryEnabled;
    /**
     * Fully defined path to the git repository.
     * The path should be a valid HTTP URL.
     */
    private URI httpUrlToRepo;
    /**
     * Fully defined path to the project ReadMe file.
     * The path should be a valid HTTP URL.
     */
    private URI readmeUrl;
}
