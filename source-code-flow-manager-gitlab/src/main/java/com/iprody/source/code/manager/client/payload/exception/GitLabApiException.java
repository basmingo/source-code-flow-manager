package com.iprody.source.code.manager.client.payload.exception;

/**
 * The class represents a custom exception for handle Gitlab API's exceptions.
 */
public class GitLabApiException extends RuntimeException {

    /**
     * @param exceptionMessage description of exception.
     */
    public GitLabApiException(final String exceptionMessage) {
        super(exceptionMessage);
    }

    /**
     * @param exceptionMessage description of exception.
     * @param throwable        throwable object.
     */
    public GitLabApiException(final String exceptionMessage, final Throwable throwable) {
        super(exceptionMessage, throwable);
    }
}
