package com.iprody.source.code.flow.manager.pipeline_job_api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Class represents a pipeline job and its vital parts on inner API side.
 */
@Data
public class Job {

    /**
     * Failure status.
     */
    @JsonProperty("allow_failure")
    private boolean allowFailure;

    /**
     * Time of creation of the job.
     */
    @JsonProperty("created_at")
    private Date createdAt;

    /**
     * Time of starting of the job.
     */
    @JsonProperty("started_at")
    private Date startedAt;

    /**
     * Time of finishing of the job.
     */
    @JsonProperty("finished_at")
    private Date finishedAt;

    /**
     * Duration of executing of the job.
     */
    private double duration;

    /**
     * Duration of queued.
     */
    @JsonProperty("queued_duration")
    private double queuedDuration;

    /**
     * Time of artifact expiration.
     */
    @JsonProperty("artifacts_expire_at")
    private Date artifactsExpireAt;

    /**
     * Tag list of the job.
     */
    @JsonProperty("tag_list")
    private List<String> tagList;

    /**
     * Id of the job.
     */
    private int id;

    /**
     * Name of the job.
     */
    private String name;

    /**
     * Reference of the job.
     */
    private String ref;

    /**
     * Stage of the job.
     */
    private String stage;

    /**
     * Status of the job.
     */
    private String status;

    /**
     * Failure reason.
     */
    @JsonProperty("failure_reason")
    private String failureReason;

    /**
     * Tag of the job.
     */
    private boolean tag;

    /**
     * Url to the job.
     */
    private String webUrl;
}
