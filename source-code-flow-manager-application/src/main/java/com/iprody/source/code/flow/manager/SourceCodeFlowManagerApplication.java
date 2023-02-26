package com.iprody.source.code.flow.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class SourceCodeFlowManagerApplication {

    private SourceCodeFlowManagerApplication() {

    }

    /**
     * Entry point of the service.
     * @param args startup arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SourceCodeFlowManagerApplication.class, args);
    }
}
