package org.jtwig.plugins.github;

public class GithubRelease {
    private final String owner;
    private final String repository;
    private final String tagName;

    public GithubRelease(String owner, String repository, String tagName) {
        this.owner = owner;
        this.repository = repository;
        this.tagName = tagName;
    }

    public String getOwner() {
        return owner;
    }

    public String getRepository() {
        return repository;
    }

    public String getTagName() {
        return tagName;
    }
}
