package com.iprody.source.code.flow.manager.core.pipeline;

/**
 * Represents the possible status values for a GitPipeline.
 */
public enum GitPipelineStatus {

    /**
     * status CREATED for pipeline creation.
     */
    CREATED,

    /**
     * status WAITING_FOR_RESPONSE for pipeline.
     */
    WAITING_FOR_RESPONSE,

    /**
     * status PREPARING for pipeline.
     */
    PREPARING,

    /**
     * status PENDING for pipeline.
     */
    PENDING,

    /**
     * status RUNNING for pipeline.
     */
    RUNNING,

    /**
     * status SUCCESS for pipeline.
     */
    SUCCESS,

    /**
     * status FAILED for pipeline.
     */
    FAILED,

    /**
     * status CANCELED for pipeline.
     */
    CANCELED,

    /**
     * status SKIPPED for pipeline.
     */
    SKIPPED,

    /**
     * status MANUAL for pipeline.
     */
    MANUAL,

    /**
     * status SCHEDULED for pipeline.
     */
    SCHEDULED;
}
