package com.iprody.source.code.flow.manager.core.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import java.net.URI;
import java.time.Instant;

/**
 * Class represents a git project and its vital parts in a vendor
 * related implementation (e.g. Gitlab, Github).
 */
@Getter
@Setter
@NoArgsConstructor
public class GitProject {

    /**
     * ID of the project.
     */
    private long id;

    /**
     * Name of the project.
     */
    @NonNull
    private String name;

    /**
     * Namespace of the project (e.g. organisation, group, team, etc.)
     * provides one place to organize related projects.
     */
    private String namespace;

    /**
     * Description for the project.
     */
    private String description;

    /**
     * Enable visibility of a new project.
     */
    private boolean isVisible;

    /**
     * Base branch of a new project.
     */
    private String baseBranch;

    /**
     * Date and time of the project creation.
     */
    private Instant createdAT;

    /**
     * Person ID of the project creator.
     */
    private long creatorID;

    /**
     * Project owner name.
     */
    private String owner;

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

    /**
     * Enable Issues for a new project.
     */
    private boolean isIssuesEnabled;
}
