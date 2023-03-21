package com.iprody.source.code.flow.manager.audit.logging.audit;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParameterUtilsTest {
    private final String id = "id";
    private final String idValue = "1";
    private final String namespace = "namespace";
    private final String namespaceValue = "value";

    @Test
    void mapsShouldBeEquals() {
        final Object[] args = {idValue, namespaceValue};
        final String[] parameterNames = {id, namespace};
        final Map<String, Object> expectedParameters = Map.of(
                id, idValue,
                namespace, namespaceValue
        );

        final Map<String, Object> actualParameters = ParameterUtils.getParameters(parameterNames, args);

        assertThat(expectedParameters).isEqualTo(actualParameters);
    }

    @Test
    void whenDifferentMaps_thenMapsShouldNotBeEquals() {
        final Object[] args = {idValue, namespaceValue};
        final String[] parameterNames = {id, namespace};
        final Map<String, Object> expectedParameters = Map.of(
                "projectId", idValue,
                namespace, namespaceValue
        );

        final Map<String, Object> actualParameters = ParameterUtils.getParameters(parameterNames, args);

        assertThat(expectedParameters).isNotEqualTo(actualParameters);
    }

    @Test
    void whenDifferentLengthOfArrays_thenShouldThrowException() {
        final Object[] args = {idValue, namespaceValue, "thirdArg"};
        final String[] parameterNames = {id, namespace};
        final Map<String, Object> expectedParameters = Map.of(
                id, idValue,
                namespace, namespaceValue
        );

        assertThatThrownBy(() -> ParameterUtils.getParameters(parameterNames, args))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Arrays have different lengths");
    }
}
