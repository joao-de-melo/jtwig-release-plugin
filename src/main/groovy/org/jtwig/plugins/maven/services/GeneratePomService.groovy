package org.jtwig.plugins.maven.services

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.artifacts.UnknownConfigurationException
import org.jtwig.plugins.config.ReleaseExtension
import org.jtwig.plugins.config.ReleaseMavenExtension
import org.jtwig.plugins.maven.model.MavenDependency
import org.jtwig.plugins.maven.model.PomFile


class GeneratePomService {
    public PomFile generatePom (Project project) {
        ReleaseExtension releaseExtension = ReleaseExtension.retrieve(project);
        ReleaseMavenExtension releaseMavenExtension = ReleaseMavenExtension.retrieve(project);
        List<MavenDependency> dependencies = new ArrayList<>();

        String groupId = (String) project.getGroup();
        String artifactId = project.getName();
        String version = releaseExtension.getVersion();

        for (Map.Entry<String, String> entry : releaseMavenExtension.getConfigurationsScope().entrySet()) {
            try {
                Iterable<ResolvedDependency> artifacts = project
                        .getConfigurations().getByName(entry.getKey())
                        .getResolvedConfiguration().getFirstLevelModuleDependencies();
                for (ResolvedDependency artifact : artifacts) {
                    dependencies.add(new MavenDependency(
                            artifact.getModuleGroup(),
                            artifact.getModuleName(),
                            artifact.getModuleVersion(),
                            entry.getValue()
                    ));
                }
            } catch (UnknownConfigurationException e) {
                // swallow
            }
        }

        return new PomFile(
                groupId,
                artifactId,
                version,
                dependencies
        );
    }
}
