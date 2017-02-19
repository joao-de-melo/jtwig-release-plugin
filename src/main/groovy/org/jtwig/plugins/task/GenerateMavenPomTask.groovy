package org.jtwig.plugins.task

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.jtwig.plugins.maven.model.PomFile
import org.jtwig.plugins.maven.services.GeneratePomService
import org.jtwig.plugins.maven.services.WritePomXmlService
import org.jtwig.plugins.util.ProducedFile

class GenerateMavenPomTask extends DefaultTask {
    public static final String TASK_NAME = "jtwigReleaseGeneratePom";

    public static void create (Project project) {
        project.task(TASK_NAME, type: GenerateMavenPomTask);
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Generates pom.xml"
    }

    @TaskAction
    public void generatePom() {
        GeneratePomService generatePomService = new GeneratePomService();
        WritePomXmlService writePomXml = new WritePomXmlService();

        PomFile pomFile = generatePomService.generatePom(getProject());
        File output = ProducedFile.POM.file(getProject());

        writePomXml.write(pomFile, output);
    }
}
