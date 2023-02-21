package com.iprody.source.code.flow.manager.communication.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WebClientConfiguration.class)
class WebClientConfigurationTest {

    @Test
    void shouldConstructApplicationContextSuccessfully_andRequiredBeansAreReadyForInjection(ApplicationContext context) {
        assertThat(context.getBean("webClient")).isNotNull();
    }
}
