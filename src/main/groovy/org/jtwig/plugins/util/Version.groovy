package org.jtwig.plugins.util

import org.gradle.api.Project
import org.jtwig.plugins.config.ReleaseExtension


public class Version {
    public static String version (Project project) {
        return ReleaseExtension.retrieve(project).getVersion();
    }
}
