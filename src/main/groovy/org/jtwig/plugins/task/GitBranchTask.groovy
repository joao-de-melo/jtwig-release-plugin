package org.jtwig.plugins.task

import org.apache.commons.lang3.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.jtwig.plugins.config.ReleaseExtension
import org.jtwig.plugins.config.ReleaseGitExtension
import org.jtwig.plugins.environment.Environment
import org.jtwig.plugins.git.GitBranchRequest
import org.jtwig.plugins.git.GitBranchService


class GitBranchTask extends DefaultTask {
    public static final String TASK_NAME = "jtwigReleaseGitBranch";
    public static final String VERSION_FILE = "jtwig.version"

    public static File versionFile(File directory) {
        new File(directory, VERSION_FILE)
    }

    public static void create (Project project) {
        project.task(TASK_NAME, type: GitBranchTask);
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Pushes new branch to git repository"
    }

    @TaskAction
    void branch() {
        ReleaseExtension extension = ReleaseExtension.retrieve(getProject());
        if (StringUtils.isBlank(extension.getGit().getUsername())) throw new RuntimeException(String.format("%s.git.username is undefined", ReleaseExtension.EXTENSION));
        if (StringUtils.isBlank(extension.getGit().getPassword())) throw new RuntimeException(String.format("%s.git.password is undefined", ReleaseExtension.EXTENSION));

        ReleaseGitExtension gitExtension = extension.getGit();

        new GitBranchService().createBranch(
                new GitBranchRequest(
                        new File(getProject().getBuildDir(), "git-release"),
                        gitExtension.getUsername(),
                        gitExtension.getPassword(),
                        gitExtension.getBaseUrl(),
                        gitExtension.getOwner(),
                        gitExtension.getRepository(),
                        Environment.version(TriggerTravisTask.VERSION_VARIABLE).get(),
                        VERSION_FILE
                )
        );
    }
}
