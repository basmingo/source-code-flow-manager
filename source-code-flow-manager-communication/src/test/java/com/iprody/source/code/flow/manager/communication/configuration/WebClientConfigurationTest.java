package com.iprody.source.code.flow.manager.communication.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class represents tests for testing WebClientConfiguration.
 */
@SpringBootTest(classes = WebClientConfiguration.class)
class WebClientConfigurationTest {

    /**
     * Testing creation WebClient Bean in ApplicationContext.
     *
     * @param context this created and contains Bean named webClient
     */
    @Test
    @DisplayName("shouldConstructApplicationContextSuccessfully_andRequiredBeansAreReadyForInjection")
    void requiredBeanAreReadyForInjection(ApplicationContext context) {
        assertThat(context.getBean("webClient")).isNotNull();
    }
}
