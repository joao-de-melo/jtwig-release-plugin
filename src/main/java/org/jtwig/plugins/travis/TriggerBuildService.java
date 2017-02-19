package org.jtwig.plugins.travis;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.jtwig.plugins.util.UrlBuilder;

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
            String url = UrlBuilder.url(request.getBaseUrl())
                    .addToPath("repo")
                    .addToPathEscaped(request.getProject())
                    .addToPath("requests")
                    .build();
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

    public void trigger(String baseUrl, String token, String project, String version) {
        HttpResponse response;
        try {

            String content = request(version);
            StringEntity entity = new StringEntity(content);
            String url = UrlBuilder.url(baseUrl)
                    .addToPath("repo")
                    .addToPathEscaped(project)
                    .addToPath("requests")
                    .build();
            HttpPost post = new HttpPost(url);
            post.addHeader("Travis-API-Version", "3");
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Authorization", String.format("token %s", token));

            post.setEntity(entity);

            response = httpClient.execute(post);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot trigger release of project %s", project));
        }

        if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300)
            throw new RuntimeException(String.format("Cannot trigger release of project %s", project));
    }

    private String request(String version) {
        return new JSONObject()
                .put("request",
                        new JSONObject()
                                .put("message", String.format("Version %s release triggered", version))
                                .put("config", new JSONObject()
                                        .put("env", new JSONObject()
                                                .put("JTWIG_VERSION", version)
                                        )
                                )
                ).toString();
    }
}
