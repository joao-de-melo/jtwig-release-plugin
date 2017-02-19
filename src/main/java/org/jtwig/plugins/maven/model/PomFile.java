package org.jtwig.plugins.maven.model;

import java.util.List;

public class PomFile {
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final List<MavenDependency> dependencies;

    public PomFile(String groupId, String artifactId, String version, List<MavenDependency> dependencies) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.dependencies = dependencies;
    }

    public List<MavenDependency> getDependencies() {
        return dependencies;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }
}
