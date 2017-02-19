package org.jtwig.plugins.bintray.http.auth;

public class BintrayAuthentication {
    private final String username;
    private final String apiKey;

    public BintrayAuthentication(String username, String apiKey) {
        this.username = username;
        this.apiKey = apiKey;
    }

    public String getUsername() {
        return username;
    }

    public String getApiKey() {
        return apiKey;
    }
}
