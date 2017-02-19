package org.jtwig.plugins.task

import org.apache.commons.lang3.StringUtils
import org.apache.http.impl.client.HttpClients
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.jtwig.plugins.config.ReleaseExtension
import org.jtwig.plugins.github.CreateReleaseRequest
import org.jtwig.plugins.github.CreateReleaseService
import org.jtwig.plugins.github.GithubRelease
import org.jtwig.plugins.github.GithubUser


class GithubReleaseTask extends DefaultTask {
    public static final String TASK_NAME = "jtwigReleaseGithubRelease";

    public static void create (Project project) {
        project.task(TASK_NAME, type: GithubReleaseTask)
                .dependsOn(GitBranchTask.TASK_NAME);
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Creates github release"
    }

    @TaskAction
    public void release () {
        ReleaseExtension extension = ReleaseExtension.retrieve(getProject());

        if (StringUtils.isBlank(extension.getGithub().getUsername())) throw new RuntimeException(String.format("%s.github.username is undefined", ReleaseExtension.EXTENSION));
        if (StringUtils.isBlank(extension.getGithub().getToken())) throw new RuntimeException(String.format("%s.github.token is undefined", ReleaseExtension.EXTENSION));

        GithubUser githubUser = new GithubUser(extension.getGithub().getUsername(), extension.getGithub().getToken());
        GithubRelease githubRelease = new GithubRelease(
                extension.getGithub().getOwner(),
                extension.getGithub().getRepository(),
                extension.getVersion());

        CreateReleaseService createReleaseService = new CreateReleaseService(HttpClients.createDefault());

        if (!createReleaseService.releaseExists(extension.getGithub().getApiBaseUrl(), githubUser, githubRelease)) {
            createReleaseService.release(extension.getGithub().getApiBaseUrl(), new CreateReleaseRequest(
                    githubUser,
                    githubRelease,
                    extension.getVersion(),
                    "Release "+extension.getVersion(),
                    extension.getVersion()
            ));
        }
    }
}
