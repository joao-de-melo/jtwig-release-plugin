package org.jtwig.plugins.util

import org.gradle.api.Project
import org.jtwig.plugins.environment.Environment
import org.jtwig.plugins.task.TriggerTravisTask

public class Version {
    public static String version (Project project) {
        return Environment.version(TriggerTravisTask.VERSION_VARIABLE).get();
    }
}
