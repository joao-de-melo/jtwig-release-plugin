package org.jtwig.plugins.task

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.jtwig.plugins.util.DestinationDir
import org.jtwig.plugins.util.Version

class GenerateJarTask extends Jar {
    public static final String TASK_NAME = "jtwigReleaseGenerateJar";

    public static void create (Project project) {
        project.task(TASK_NAME, type: GenerateJarTask).dependsOn("classes");
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Generates compiled jar"
    }

    @Override
    protected void copy() {
        setDestinationDir(DestinationDir.destinationDir(getProject()));
        setVersion(Version.version(getProject()))
        super.copy()
    }
}
