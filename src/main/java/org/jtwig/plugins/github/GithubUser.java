package org.jtwig.plugins.github;

public class GithubUser {
    private final String user;
    private final String token;

    public GithubUser(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
