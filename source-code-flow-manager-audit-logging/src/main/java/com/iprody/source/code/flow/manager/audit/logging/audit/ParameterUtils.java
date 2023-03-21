package com.iprody.source.code.flow.manager.audit.logging.audit;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for generating a map of method parameters and their corresponding values.
 */
@UtilityClass
public class ParameterUtils {

    /**
     * Returns a map of method parameters and their corresponding values.
     *
     * @param params an array of parameter names
     * @param args   an array of argument values
     * @return a map containing parameter names as keys and argument values as values
     * @throws IllegalArgumentException if the lengths of the params and args arrays are not equal
     */
    @SneakyThrows
    public static Map<String, Object> getParameters(String[] params, Object[] args) {
        if (params.length != args.length) {
            throw new IllegalArgumentException("Arrays have different lengths");
        }

        final var parameters = new HashMap<String, Object>();

        for (int i = 0; i < params.length; i++) {
            parameters.put(params[i], args[i]);
        }
        return parameters;
    }
}
