package org.jtwig.plugins.bintray.services.model;

import org.jtwig.plugins.bintray.model.BintrayPackageVersion;

import java.io.File;

public class UploadArtifactRequest {
    private final BintrayPackageVersion packageVersion;
    private final String artifactPath;
    private final boolean override;
    private final File file;

    public UploadArtifactRequest(BintrayPackageVersion packageVersion, String artifactPath, boolean override, File file) {
        this.packageVersion = packageVersion;
        this.artifactPath = artifactPath;
        this.override = override;
        this.file = file;
    }

    public BintrayPackageVersion getPackageVersion() {
        return packageVersion;
    }

    public String getArtifactPath() {
        return artifactPath;
    }

    public File getFile() {
        return file;
    }

    public boolean isOverride() {
        return override;
    }
}
