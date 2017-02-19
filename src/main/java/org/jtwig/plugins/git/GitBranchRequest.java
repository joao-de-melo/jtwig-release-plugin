package org.jtwig.plugins.git;

import java.io.File;

public class GitBranchRequest {
    private final File directory;
    private final String username;
    private final String password;
    private final String baseUrl;
    private final String owner;
    private final String repository;
    private final String version;
    private final String versionFileName;

    public GitBranchRequest(File directory, String username, String password, String baseUrl, String owner, String repository, String version, String versionFileName) {
        this.directory = directory;
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
        this.owner = owner;
        this.repository = repository;
        this.version = version;
        this.versionFileName = versionFileName;
    }

    public File getDirectory() {
        return directory;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getOwner() {
        return owner;
    }

    public String getRepository() {
        return repository;
    }

    public String getVersion() {
        return version;
    }

    public String getVersionFileName() {
        return versionFileName;
    }
}
