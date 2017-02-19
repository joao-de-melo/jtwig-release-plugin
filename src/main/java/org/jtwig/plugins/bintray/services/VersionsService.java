package org.jtwig.plugins.bintray.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONObject;
import org.jtwig.plugins.bintray.http.BintrayHttpClient;
import org.jtwig.plugins.bintray.model.BintrayPackage;
import org.jtwig.plugins.bintray.services.model.CreateVersionRequest;
import org.jtwig.plugins.util.ResponseUtils;
import org.jtwig.plugins.util.UrlBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VersionsService {
    private final BintrayHttpClient httpClient;

    public VersionsService(BintrayHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean create(String baseUrl, CreateVersionRequest request) {
        String url = UrlBuilder.url(baseUrl)
                .addToPath("packages")
                .addToPath(request.getPackageVersion().getPackage().getPath())
                .addToPath("versions")
                .build();

        HttpPost post = new HttpPost(url);
        String content = new JSONObject()
                .put("name", request.getPackageVersion().getName())
                .put("desc", request.getDescription())
                .put("released", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(new Date()))
                .toString();

        post.setEntity(new ByteArrayEntity(content.getBytes()));
        post.setHeader("Content-Type", "application/json");
        HttpResponse httpResponse = httpClient.execute(post);
        return httpResponse.getStatusLine().getStatusCode() == 201;
    }

    public boolean versionExists (String baseUrl, BintrayPackage repository, String version) {
        String url = UrlBuilder.url(baseUrl)
                .addToPath("packages")
                .addToPath(repository.getPath())
                .addToPath("versions")
                .addToPath(version)
                .build();

        HttpGet get = new HttpGet(url);
        HttpResponse result = httpClient.execute(get);

        if (result.getStatusLine().getStatusCode() != 200) return false;

        String content = ResponseUtils.getContent(result);
        return new JSONObject(content)
                .getString("name").equals(version);
    }
}
