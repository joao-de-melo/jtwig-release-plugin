package org.jtwig.plugins.model;

public class TravisRequestConfig {
    private String baseUrl = "https://api.travis-ci.org";
    private String token = null;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
