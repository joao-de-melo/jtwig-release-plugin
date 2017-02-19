package org.jtwig.plugins.task

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.jtwig.plugins.util.DestinationDir
import org.jtwig.plugins.util.Version

class GenerateJavadocJarTask extends Jar {
    public static final String TASK_NAME = "jtwigReleaseGenerateJavadocJar";

    public static void create (Project project) {
        project.task(TASK_NAME, type: GenerateJavadocJarTask).dependsOn("javadoc");
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Generates javadoc jar"
    }

    @Override
    protected void copy() {
        setDestinationDir(DestinationDir.destinationDir(getProject()));
        setVersion(Version.version(getProject()));
        setClassifier("javadoc");
        from(getProject().getTasks().javadoc.destinationDir);
        super.copy()
    }
}
