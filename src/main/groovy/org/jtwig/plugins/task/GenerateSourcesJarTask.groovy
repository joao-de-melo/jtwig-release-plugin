package org.jtwig.plugins.task

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.jtwig.plugins.util.DestinationDir
import org.jtwig.plugins.util.Version

class GenerateSourcesJarTask extends Jar {
    public static final String TASK_NAME = "jtwigReleaseGenerateSourcesJar";

    public static void create (Project project) {
        project
                .task(TASK_NAME, type: GenerateSourcesJarTask)
            .dependsOn("classes");
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Generates sources jar"
    }

    @Override
    protected void copy() {
        setDestinationDir(DestinationDir.destinationDir(getProject()));
        setVersion(Version.version(getProject()));
        setClassifier("sources");
        from(getProject().sourceSets.main.allSource);
        super.copy()
    }
}
