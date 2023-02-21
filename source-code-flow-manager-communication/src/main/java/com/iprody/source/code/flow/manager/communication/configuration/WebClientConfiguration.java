package com.iprody.source.code.flow.manager.communication.configuration;

import com.iprody.source.code.flow.manager.communication.WebClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private static final String BASE_DIR = "https://gitlab.com/api/v4/projects/";

    private static final String GITLAB_TOKEN = "GITLAB-TOKEN";

    @Bean("webClient")
    public WebClient webClient() {
        return new WebClientBuilder(BASE_DIR)
                .authToken(GITLAB_TOKEN)
                .build();
    }
}
