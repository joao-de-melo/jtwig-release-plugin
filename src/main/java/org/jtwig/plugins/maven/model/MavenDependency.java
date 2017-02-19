package org.jtwig.plugins.maven.model;

public class MavenDependency {
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String scope;

    public MavenDependency(String groupId, String artifactId, String version, String scope) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.scope = scope;
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

    public String getScope() {
        return scope;
    }
}
