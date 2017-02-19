package org.jtwig.plugins.bintray.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.json.JSONObject;
import org.jtwig.plugins.bintray.http.BintrayHttpClient;
import org.jtwig.plugins.bintray.model.BintrayPackage;
import org.jtwig.plugins.bintray.model.License;
import org.jtwig.plugins.bintray.services.model.CreatePackageRequest;
import org.jtwig.plugins.util.ResponseUtils;
import org.jtwig.plugins.util.UrlBuilder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class PackagesService {
    private final BintrayHttpClient httpClient;

    public PackagesService(BintrayHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean exists (String baseUrl, BintrayPackage bintrayPackage) {
        HttpGet httpGet = new HttpGet(UrlBuilder.url(baseUrl)
                .addToPath("/packages")
                .addToPath(bintrayPackage.getPath())
                .build());

        HttpResponse result = httpClient.execute(httpGet);

        if (result.getStatusLine().getStatusCode() == 200) {
            return bintrayPackage.getName().equals(new JSONObject(ResponseUtils.getContent(result))
                    .getString("name"));
        }
        return false;
    }

    public boolean create (String baseUrl, CreatePackageRequest request) {
        HttpPost httpPost = new HttpPost(UrlBuilder.url(baseUrl)
                .addToPath("/packages")
                .addToPath(request.getBintrayPackage().getRepository().getPath())
                .build());

        String content = new JSONObject()
                .put("name", request.getBintrayPackage().getName())
                .put("desc", request.getDescription())
                .put("labels", request.getLabels())
                .put("licenses", toValues(request.getLicenses()))
                .put("vcs_url", String.format("https://github.com/%s.gitConfig", request.getGitHubRepo()))
                .put("website_url", request.getWebsiteUrl())
                .put("issue_tracker_url", request.getIssueTrackerUrl())
                .put("github_repo", request.getGitHubRepo())
                .put("public_download_numbers", request.isPublicDownloadNumbers())
                .put("public_stats", request.isPublicStats())
                .toString();
        httpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(content.getBytes())));

        HttpResponse response = httpClient.execute(httpPost);

        return (response.getStatusLine().getStatusCode() == 201);
    }

    private List<String> toValues(List<License> licenses) {
        ArrayList<String> result = new ArrayList<>();
        for (License license : licenses) {
            result.add(license.getValue());
        }
        return result;
    }
}
