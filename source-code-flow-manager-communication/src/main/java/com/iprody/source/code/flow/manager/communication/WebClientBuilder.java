package com.iprody.source.code.flow.manager.communication;

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

/**
 * The class represents a custom WebClient builder for configuring the WebClient
 * depending on the vendor.
 */
public class WebClientBuilder {

    /**
     * The main vendor's API url.
     */
    private final String baseUrl;

    /**
     * Access token for authenticating with API.
     */
    private String defaultToken;

    /**
     * Connection attempt duration.
     */
    private Duration connTimeout;

    /**
     * Connection attempt duration (default).
     */
    private final Duration defaultConnectionTimeout = Duration.ofDays(60);

    /**
     * Response time.
     */
    private Duration respTimeout;

    /**
     * Response time (default).
     */
    private final Duration defaultResponseTimeout = Duration.ofDays(60);

    /**
     * The duration of the read, after which a ReadTimeoutException will be thrown if the read was not successful.
     */
    private final int readTimeout = 5000;

    /**
     * The duration of the write.
     * If the write is not successful within that time, a WriteTimeoutException will be thrown.
     */
    private final int writeTimeout = 5000;

    /**
     * The constructor is designed to transfer the main API url.
     *
     * @param baseUrl - main url of vendor API.
     */
    public WebClientBuilder(final String baseUrl) {
        this.baseUrl = baseUrl;
        this.connTimeout = defaultConnectionTimeout;
        this.respTimeout = defaultResponseTimeout;
    }

    /**
     * Adds access token.
     *
     * @param baseToken - access token for authenticating with API.
     * @return WebClientBuilder.
     */
    public WebClientBuilder authToken(@NonNull String baseToken) {
        this.defaultToken = baseToken;
        return this;
    }

    /**
     * Adds duration of connection timeout.
     *
     * @param connectionTimeout - duration of connection timeout.
     * @return WebClientBuilder.
     */
    public WebClientBuilder connectionTimeout(@NonNull Duration connectionTimeout) {
        this.connTimeout = connectionTimeout;
        return this;
    }

    /**
     * Adds duration of response timeout.
     *
     * @param responseTimeout - duration of response timeout.
     * @return WebClientBuilder.
     */
    public WebClientBuilder responseTimeout(@NonNull Duration responseTimeout) {
        this.respTimeout = responseTimeout;
        return this;
    }

    /**
     * Method builds WebClient.
     *
     * @return WebClient.
     */
    public WebClient build() {
        final HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connTimeout.toMillis())
                .responseTimeout(respTimeout)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)));

        final WebClient.Builder webClient = WebClient.builder();
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
