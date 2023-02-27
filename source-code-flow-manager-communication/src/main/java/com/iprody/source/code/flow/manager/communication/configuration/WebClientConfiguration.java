package com.iprody.source.code.flow.manager.communication.configuration;

import com.iprody.source.code.flow.manager.communication.WebClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * The class represents a configuration containing ready to use and inject into the WebClient Bean APIs.
 */
@Configuration
public class WebClientConfiguration {

    /**
     * The main vendor's API url.
     */
    private static final String BASE_DIR = "https://gitlab.com/api/v4/projects/";

    /**
     * Connection attempt duration.
     */
    private final int connectionTimeout = 60;

    /**
     * Response time.
     */
    private final int responseTimeout = 60;

    /**
     * Bean of WebClient.
     *
     * @return configured WebClient
     */
    @Bean("webClient")
    public WebClient webClient() {

        return new WebClientBuilder(BASE_DIR)
                .connectionTimeout(Duration.ofDays(connectionTimeout))
                .responseTimeout(Duration.ofSeconds(responseTimeout))
                .authToken("GITLAB_TOKEN")
                .build();
    }
}
