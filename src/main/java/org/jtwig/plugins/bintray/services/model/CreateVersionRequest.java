package org.jtwig.plugins.bintray.services.model;

import org.jtwig.plugins.bintray.model.BintrayPackageVersion;

public class CreateVersionRequest {
    private final BintrayPackageVersion packageVersion;
    private final String description;

    public CreateVersionRequest(BintrayPackageVersion packageVersion, String description) {
        this.packageVersion = packageVersion;
        this.description = description;
    }

    public BintrayPackageVersion getPackageVersion() {
        return packageVersion;
    }

    public String getDescription() {
        return description;
    }
}
