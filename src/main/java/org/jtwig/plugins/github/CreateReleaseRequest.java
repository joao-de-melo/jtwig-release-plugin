package org.jtwig.plugins.github;

public class CreateReleaseRequest {
    private final GithubUser user;
    private final GithubRelease release;
    private final String name;
    private final String description;
    private final String target;

    public CreateReleaseRequest(GithubUser user, GithubRelease release, String name, String description, String target) {
        this.user = user;
        this.release = release;
        this.name = name;
        this.description = description;
        this.target = target;
    }

    public GithubUser getUser() {
        return user;
    }

    public GithubRelease getRelease() {
        return release;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTarget() {
        return target;
    }
}
