package com.iprody.source.code.flow.manager.pipeline_job_api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Job {
    @JsonProperty("allow_failure")
    private boolean allowFailure;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("started_at")
    private Date startedAt;
    @JsonProperty("finished_at")
    private Date finishedAt;
    private double duration;
    @JsonProperty("queued_duration")
    private double queuedDuration;
    @JsonProperty("artifacts_expire_at")
    private Date artifactsExpireAt;
    @JsonProperty("tag_list")
    private List<String> tagList;
    private int id;
    private String name;
    private String ref;
    private List<Object> artifacts;
    private String stage;
    private String status;
    @JsonProperty("failure_reason")
    private String failureReason;
    private boolean tag;
    private String webUrl;
}
