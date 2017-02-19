package org.jtwig.plugins.model;

import java.util.List;

public class TravisConfig {
    private TravisRequestConfig request = new TravisRequestConfig();
    private List<String> upstreamProjects;

    public List<String> getUpstreamProjects() {
        return upstreamProjects;
    }

    public void setUpstreamProjects(List<String> upstreamProjects) {
        this.upstreamProjects = upstreamProjects;
    }

    public TravisRequestConfig getRequest() {
        return request;
    }

    public void setRequest(TravisRequestConfig request) {
        this.request = request;
    }
}
