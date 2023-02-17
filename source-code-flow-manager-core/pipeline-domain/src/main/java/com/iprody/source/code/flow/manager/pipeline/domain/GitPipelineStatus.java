package com.iprody.source.code.flow.manager.pipeline.domain;

/**
 * Represents the possible status values for a GitPipeline.
 */
public enum GitPipelineStatus {
    CREATED,
    WAITING_FOR_RESPONSE,
    PREPARING,
    PENDING,
    RUNNING,
    SUCCESS,
    FAILED,
    CANCELED,
    SKIPPED,
    MANUAL,
    SCHEDULED;
}
