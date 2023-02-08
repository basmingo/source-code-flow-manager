package com.iprody.source.code.flow.manager.utils;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebClientBuilder {
    private final String baseUrl;
    private String defaultToken;
    private Duration connectionTimeout;
    private Duration responseTimeout;

    public WebClientBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
        this.connectionTimeout = Duration.ofSeconds(60);
        this.responseTimeout = Duration.ofSeconds(60);
    }

    public WebClientBuilder authToken(@NonNull String baseToken) {
        this.defaultToken = baseToken;
        return this;
    }

    public WebClientBuilder connectionTimeout(@NonNull Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public WebClientBuilder responseTimeout(@NonNull Duration responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public WebClient build() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connectionTimeout.toMillis())
                .responseTimeout(responseTimeout)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        WebClient.Builder webClient = WebClient.builder();
        if (baseUrl != null) {
            webClient.baseUrl(baseUrl);
        }

        if (defaultToken != null) {
            webClient.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + defaultToken);
        }

        return webClient
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
