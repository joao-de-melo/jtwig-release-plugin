package org.jtwig.plugins.travis;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

public class TriggerBuildService {
    private final HttpClient httpClient;
    private final TriggerBuilderBodyService bodyService;

    public TriggerBuildService(HttpClient httpClient, TriggerBuilderBodyService bodyService) {
        this.httpClient = httpClient;
        this.bodyService = bodyService;
    }

    public void trigger(TriggerBuildRequest request) {
        HttpResponse response;
        try {
            JSONObject jsonObject = bodyService.generate(request);
            StringEntity entity = new StringEntity(jsonObject.toString());
            String url = String.format("%s/repo/%s/requests", request.getBaseUrl(), request.getProject());
            HttpPost post = new HttpPost(url);
            post.addHeader("Travis-API-Version", "3");
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Authorization", String.format("token %s", request.getToken()));

            post.setEntity(entity);

            response = httpClient.execute(post);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot trigger upstream project %s build", request.getProject()));
        }

        if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300)
            throw new RuntimeException(String.format("Cannot trigger upstream project %s build", request.getProject()));
    }
}
