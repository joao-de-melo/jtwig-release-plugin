package org.jtwig.plugins.bintray.services.model;

import org.jtwig.plugins.bintray.model.BintrayPackage;
import org.jtwig.plugins.bintray.model.License;

import java.util.List;

public class CreatePackageRequest {
    private final BintrayPackage bintrayPackage;
    private final String description;
    private final List<String> labels;
    private final List<License> licenses;
    private final String githubRepo;
    private final String websiteUrl;
    private final String issueTrackerUrl;
    private final boolean publicDownloadNumbers;
    private final boolean publicStats;

    public CreatePackageRequest(BintrayPackage bintrayPackage, String description, List<String> labels, List<License> licenses, String githubRepo, String websiteUrl, String issueTrackerUrl, boolean publicDownloadNumbers, boolean publicStats) {
        this.bintrayPackage = bintrayPackage;
        this.description = description;
        this.labels = labels;
        this.licenses = licenses;
        this.githubRepo = githubRepo;
        this.websiteUrl = websiteUrl;
        this.issueTrackerUrl = issueTrackerUrl;
        this.publicDownloadNumbers = publicDownloadNumbers;
        this.publicStats = publicStats;
    }

    public BintrayPackage getBintrayPackage() {
        return bintrayPackage;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public String getGitHubRepo() {
        return githubRepo;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getIssueTrackerUrl() {
        return issueTrackerUrl;
    }

    public boolean isPublicDownloadNumbers() {
        return publicDownloadNumbers;
    }

    public boolean isPublicStats() {
        return publicStats;
    }
}
