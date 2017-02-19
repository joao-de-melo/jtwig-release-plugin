package org.jtwig.plugins.environment;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;

public class Environment {
    public static Optional<String> version (String variable) {
        Optional<String> result = environment(variable);
        if (result.isPresent()) return result;
        try {
            String property = System.getProperty(variable);
            if (StringUtils.isBlank(property)) {
                return Optional.absent();
            }
            return Optional.of(property);
        } catch (Exception e) {
            return Optional.absent();
        }
    }

    private static Optional<String> environment (String variable) {
        try {

            String property = System.getenv(variable);
            if (StringUtils.isBlank(property)) {
                return Optional.absent();
            }
            return Optional.of(property);
        } catch (Exception e) {
            return Optional.absent();
        }
    }
}
