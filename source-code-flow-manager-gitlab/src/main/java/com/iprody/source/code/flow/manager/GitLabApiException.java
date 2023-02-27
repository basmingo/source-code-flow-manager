package com.iprody.source.code.flow.manager;

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
}
