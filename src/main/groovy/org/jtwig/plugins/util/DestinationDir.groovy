package org.jtwig.plugins.util

import org.gradle.api.Project
import org.jtwig.plugins.config.ReleaseExtension


public class DestinationDir {
    public static File destinationDir(Project project) {
        File file = new File(project.getBuildDir(), ReleaseExtension.retrieve(project).getDestinationDir());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
