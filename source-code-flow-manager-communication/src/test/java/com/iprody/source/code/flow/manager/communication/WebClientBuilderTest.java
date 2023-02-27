package com.iprody.source.code.flow.manager.communication;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class represents tests for testing WebClientBuilder.
 */
public class WebClientBuilderTest {
    /**
     * The field represents test baseDir.
     */
    private static final String DEFAULT_URI = "testingUri";
    /**
     * The field represents test access token.
     */
    private static final String DEFAULT_TOKEN = "testingToken";

    /**
     * The method creates test WebClients by WebClientBuilder.
     * @return Arguments with configured WebClients.
     */
    public static Stream<Arguments> webClientBuilderParameters() {
        final int connectionTimeout = 45;
        final int responseTimeout = 45;
        return Stream.of(
                Arguments.of(new WebClientBuilder(DEFAULT_URI)
                        .build()),
                Arguments.of(new WebClientBuilder(DEFAULT_URI)
                        .authToken(DEFAULT_TOKEN)
                        .connectionTimeout(Duration.ofSeconds(connectionTimeout))
                        .responseTimeout(Duration.ofSeconds(responseTimeout))
                        .build()));
    }

    /**
     *Tests that created webClient is not null.
     *
     * @param webClient - created webclient by WebClientBuilder.
     */
    @ParameterizedTest
    @MethodSource("webClientBuilderParameters")
    void webClientBuilder_BuildsWebClient_ThatReturnsNotNull(WebClient webClient) {
        assertThat(webClient).isNotNull();
    }
}
