package org.jtwig.plugins

import com.google.common.base.Optional
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.DependencyResolveDetails
import org.jtwig.plugins.config.ReleaseExtension
import org.jtwig.plugins.environment.Environment
import org.jtwig.plugins.task.*

public class ReleasePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        ReleaseExtension extension = ReleaseExtension.create(project);
        Optional<String> version = Environment.version(extension.getVersionProperty());

        Task mainTask = project.task(JtwigReleaseTask.TASK_NAME, type: JtwigReleaseTask);

        if (version.isPresent()) {
            GenerateMavenPomTask.create(project);
            GenerateJarTask.create(project);
            GenerateJavadocJarTask.create(project);
            GenerateSourcesJarTask.create(project);
            GitBranchTask.create(project);
            GithubReleaseTask.create(project);
            UploadBintrayTask.create(project);
            TriggerTravisTask.create(project);

            mainTask.dependsOn(TriggerTravisTask.TASK_NAME);
        }

        setupDependenciesVersion(project);
    }

    private void setupDependenciesVersion(Project target) {
        ReleaseExtension extension = ReleaseExtension.retrieve(target);
        if (StringUtils.isNotBlank(extension.version)) {
            // for releases
            target.getConfigurations().all {
                resolutionStrategy {
                    eachDependency { DependencyResolveDetails details ->
                        if (details.requested.group == extension.group) {
                            details.useVersion extension.version
                        }
                    }
                }
            }
        } else {
            // for branches
            if (GitBranchTask.versionFile(target.getRootDir()).exists()) {
                String version = FileUtils.readFileToString(GitBranchTask.versionFile(target.getRootDir())).trim();
                target.getConfigurations().all {
                    resolutionStrategy {
                        eachDependency { DependencyResolveDetails details ->
                            if (details.requested.group == extension.group) {
                                details.useVersion version
                            }
                        }
                    }
                }
            }
        }
    }
}
