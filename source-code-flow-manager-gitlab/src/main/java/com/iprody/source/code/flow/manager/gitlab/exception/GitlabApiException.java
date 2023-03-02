package com.iprody.source.code.flow.manager.gitlab.exception;

/**
 * The class represents a custom exception for handle Gitlab API's exceptions.
 */
public class GitlabApiException extends RuntimeException {

    /**
     * @param exceptionMessage description of exception.
     */
    public GitlabApiException(final String exceptionMessage) {
        super(exceptionMessage);
    }

    /**
     * @param exceptionMessage description of exception.
     * @param throwable        throwable object.
     */
    public GitlabApiException(final String exceptionMessage, final Throwable throwable) {
        super(exceptionMessage, throwable);
    }
}
