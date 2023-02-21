package com.iprody.source.code.flow.manager.communication;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WebClientBuilderTest {
    private static final String DEFAULT_URI = "testingUri";
    private static final String DEFAULT_TOKEN = "testingToken";

    public static Stream<Arguments> webClientBuilderParameters() {
        return Stream.of(
                Arguments.of(new WebClientBuilder(DEFAULT_URI)
                        .build()),
                Arguments.of(new WebClientBuilder(DEFAULT_URI)
                        .authToken(DEFAULT_TOKEN)
                        .connectionTimeout(Duration.ofSeconds(45))
                        .responseTimeout(Duration.ofSeconds(45))
                        .build()));
    }

    @ParameterizedTest
    @MethodSource("webClientBuilderParameters")
    void webClientBuilder_BuildsWebClient_ThatReturnsNotNull(WebClient webClient) {
        assertThat(webClient).isNotNull();
    }
}
