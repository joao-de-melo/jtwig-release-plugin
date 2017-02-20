package org.jtwig.plugins.task

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.jtwig.plugins.environment.Environment
import org.jtwig.plugins.util.DestinationDir

class GenerateJarTask extends Jar {
    public static final String TASK_NAME = "jtwigReleaseGenerateJar";

    public static void create (Project project) {
        project.task(TASK_NAME, type: GenerateJarTask)
                .doFirst({
            version = Environment.version(TriggerTravisTask.VERSION_VARIABLE).get()
            destinationDir = DestinationDir.destinationDir(project)
            from project.sourceSets.main.output
        }).dependsOn("classes");
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Generates compiled jar"
    }

}
