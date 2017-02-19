package org.jtwig.plugins.task

import org.apache.commons.lang3.StringUtils
import org.apache.http.impl.client.HttpClients
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.jtwig.plugins.config.ReleaseExtension
import org.jtwig.plugins.travis.TriggerBuildRequest
import org.jtwig.plugins.travis.TriggerBuildService
import org.jtwig.plugins.travis.TriggerBuilderBodyService

class TriggerTravisTask extends DefaultTask {
    public static final String TASK_NAME = "jtwigReleaseTriggerTravis";
    public static final String VERSION_VARIABLE = "JTWIG_VERSION"

    public static void create (Project project) {
        project.task(TASK_NAME, type: TriggerTravisTask)
                .dependsOn(
                GenerateMavenPomTask.TASK_NAME,
                GenerateJarTask.TASK_NAME,
                GenerateJavadocJarTask.TASK_NAME,
                GenerateSourcesJarTask.TASK_NAME,
                GithubReleaseTask.TASK_NAME,
                UploadBintrayTask.TASK_NAME
        );

    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP
    }

    @Override
    String getDescription() {
        return "Task to trigger upstream projects release"
    }

    @TaskAction
    public void trigger () {
        ReleaseExtension extension = ReleaseExtension.retrieve(getProject());

        if (StringUtils.isBlank(extension.getTravis().getToken())) throw new RuntimeException(String.format("%s.travis.token is undefined", ReleaseExtension.EXTENSION));

        TriggerBuildService service = new TriggerBuildService(
                HttpClients.createDefault(),
                new TriggerBuilderBodyService(
                        VERSION_VARIABLE,
                        extension.getVersion()
                )
        );

        String accessToken = extension.getTravis().getToken();
        for (String project : extension.getTravis().getUpstreamProjects()) {
            service.trigger(new TriggerBuildRequest(
                    getProject().name,
                    extension.getTravis().getBaseUrl(),
                    project,
                    accessToken,
                    extension.getTravis().getBranch()
            ))
        }
    }
}
