package com.iprody.source.code.flow.manager.pipeline.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;

/**
 * Represents a GitPipeline object that stores information about a Git repository's
 * pipeline configuration.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GitPipeline {
    /**
     * The ID or URL-encoded path of the project owned by
     * the authenticated user.
     */
    @NonNull
    @EqualsAndHashCode.Include
    private String id;

    /**
     * The ref of pipelines, used to specify
     * which version of the codebase to build or deploy.
     */
    private String ref;

    /**
     * The SHA of pipelines, used to identify
     * a specific commit in the Git repository.
     */
    private String sha;

    /**
     * Time of GitPipeline creation.
     */
    private Instant createdAt;

    /**
     * Last time of GitPipeline update.
     */
    private Instant updatedAt;

    /**
     * Time when the GitPipeline was started.
     */
    private Instant startedAt;

    /**
     * Time when the GitPipeline was finished.
     */
    private Instant finishedAt;

    /**
     * Time when the changes that triggered the
     * GitPipeline were committed.
     */
    private Instant committedAt;

    /**
     * Represents the status of the GitPipeline.
     */
    private GitPipelineStatus status;

    /**
     * Duration of the GitPipeline run.
     */
    private Duration duration;

    /**
     * An HTTP path to the GitPipeline.
     */
    private URI webUrl;
}
