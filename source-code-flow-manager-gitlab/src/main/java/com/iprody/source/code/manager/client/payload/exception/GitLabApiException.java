package com.iprody.source.code.manager.client.payload.exception;

public class GitLabApiException extends RuntimeException{

    public GitLabApiException(String message) {
        super(message);
    }
}
