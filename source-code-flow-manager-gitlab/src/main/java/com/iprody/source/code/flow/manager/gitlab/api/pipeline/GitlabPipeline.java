package com.iprody.source.code.flow.manager.gitlab.api.pipeline;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GitlabPipeline {

    /**
     * Gitlab pipeline ID.
     */
    private int id;

    /**
     * Pipeline coverage.
     */
    private double coverage;

    /**
     * Pipeline's before sha.
     */
    private String beforeSha;

    /**
     * Pipeline detailed status.
     */
    private DetailedStatus detailedStatus;
    /**
     * Date and time when pipeline done.
     */
    private String finishedAt;

    /**
     * Pipeline iid.
     */
    private int iid;

    /**
     * Date and time of the pipeline creation.
     */
    private String createdAt;

    /**
     * Pipeline source.
     */
    private String source;

    /**
     * Pipeline sha.
     */
    private String sha;

    /**
     * Duration of queued.
     */
    private double queuedDuration;

    /**
     * Does yaml file consist some error.
     */
    private boolean yamlErrors;

    /**
     * Duration of pipeline execution.
     */
    private double duration;

    /**
     * Pipeline reference.
     */
    private String ref;

    /**
     * Date and time of the latest pipeline update.
     */
    private String updatedAt;

    /**
     * Path where pipeline is located.
     */
    private String webUrl;

    /**
     * Pipeline's project ID.
     */
    private int projectId;

    /**
     * Date and time of commit.
     */
    private LocalDateTime committedAt;

    /**
     * Date and time of start execution.
     */
    private LocalDateTime startedAt;

    /**
     * Pipeline tag.
     */
    private boolean tag;

    /**
     * Pipeline User.
     */
    private User user;

    /**
     * Pipeline status.
     */
    private String status;

    /**
     * Constructor for injecting ID.
     *
     * @param id
     */
    public GitlabPipeline(final int id) {
        this.id = id;
    }
}
