package org.jtwig.plugins.bintray.model;

public class BintrayPackageVersion {
    private final BintrayPackage bintrayPackage;
    private final String version;

    public BintrayPackageVersion(BintrayPackage bintrayPackage, String version) {
        this.bintrayPackage = bintrayPackage;
        this.version = version;
    }

    public String getPath() {
        return bintrayPackage.getPath() + "/" + version;
    }

    public BintrayPackage getPackage() {
        return bintrayPackage;
    }

    public String getName() {
        return version;
    }
}
