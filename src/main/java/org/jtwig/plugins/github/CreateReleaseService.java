package org.jtwig.plugins.github;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.jtwig.plugins.util.UrlBuilder;

import java.io.IOException;

public class CreateReleaseService {
    private final HttpClient httpClient;

    public CreateReleaseService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean releaseExists(String baseUrl, GithubUser user, GithubRelease release) {
        HttpGet httpGet = new HttpGet(UrlBuilder.url(baseUrl)
                .addToPath("repos")
                .addToPath(release.getOwner())
                .addToPath(release.getRepository())
                .addToPath("releases")
                .addToPath("tags")
                .addToPath(release.getTagName())
                .build());

        authenticate(httpGet, user);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (IOException e) {
            return false;
        }

    }

    public void release(String baseUrl, CreateReleaseRequest request) {
        HttpResponse response;

        try {
            JSONObject jsonObject = new JSONObject()
                    .put("tag_name", request.getRelease().getTagName())
                    .put("name", request.getName())
                    .put("body", request.getDescription())
                    .put("target_commitish", request.getTarget());
            StringEntity body = new StringEntity(jsonObject.toString());
            String url = UrlBuilder.url(baseUrl)
                    .addToPath("repos")
                    .addToPath(request.getRelease().getOwner())
                    .addToPath(request.getRelease().getRepository())
                    .addToPath("releases")
                    .build();

            HttpPost post = new HttpPost(url);
            authenticate(post, request.getUser());
            post.setEntity(body);

            response = httpClient.execute(post);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create tag on github", e);
        }

        if (response.getStatusLine().getStatusCode() != 201)
            throw new RuntimeException("Unable to create tag on github");
    }

    private void authenticate(HttpUriRequest request, GithubUser githubUser) {
        request.addHeader("Authorization", String.format("Basic %s", Base64.encodeBase64String(
                String.format("%s:%s", githubUser.getUser(), githubUser.getToken()).getBytes()
        )));
    }
}
