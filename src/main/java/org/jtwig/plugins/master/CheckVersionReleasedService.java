package org.jtwig.plugins.master;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jtwig.plugins.bintray.http.BintrayHttpClient;
import org.jtwig.plugins.util.ResponseUtils;
import org.jtwig.plugins.util.UrlBuilder;

public class CheckVersionReleasedService {
    private final String baseUrl;
    private final BintrayHttpClient httpClient;

    public CheckVersionReleasedService(String baseUrl, BintrayHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public boolean released (String project, String version) {
        // /packages/:subject/:repo/:package/versions/:version/files
        HttpGet httpGet = new HttpGet(UrlBuilder.url(baseUrl)
                .addToPath("packages/jtwig/maven/")
                .addToPath(project)
                .addToPath("versions")
                .addToPath(version)
                .addToPath("files")
                .build());

        HttpResponse response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() != 200) return false;

        String content = ResponseUtils.getContent(response);

        JSONArray list = new JSONArray(content);

        for (int i = 0; i < list.length(); i++) {
            try {
                JSONObject jsonObject = list.getJSONObject(i);
                if (String.format("%s-%s.jar", project, version).equals(jsonObject.getString("name"))) {
                    return true;
                }
            } catch (Exception e) {

            }
        }

        return false;
    }
}
