package org.jtwig.plugins.travis;

public class TriggerBuildRequest {
    private final String parentProject;
    private final String baseUrl;
    private final String project;
    private final String token;

    public TriggerBuildRequest(String parentProject, String baseUrl, String project, String token, String branch) {
        this.parentProject = parentProject;
        this.baseUrl = baseUrl;
        this.project = project;
        this.token = token;
    }

    public String getParentProject() {
        return parentProject;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getProject() {
        return project;
    }

    public String getToken() {
        return token;
    }
}
