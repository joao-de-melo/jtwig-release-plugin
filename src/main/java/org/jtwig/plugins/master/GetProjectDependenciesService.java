package org.jtwig.plugins.master;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.jtwig.plugins.util.ResponseUtils;
import org.jtwig.plugins.util.UrlBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetProjectDependenciesService {
    private final Pattern PATTERN = Pattern.compile("org\\.jtwig:([^:]+)");
    private final String baseUrl;
    private final HttpClient httpClient;
    private final List<String> ignoreList;

    public GetProjectDependenciesService(String baseUrl, List<String> ignoreList) {
        this(baseUrl, HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build(), ignoreList);
    }

    public GetProjectDependenciesService(String baseUrl, HttpClient httpClient, List<String> ignoreList) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
        this.ignoreList = ignoreList;
    }

    public List<String> dependencies (String project) {
        ArrayList<String> result = new ArrayList<>();
        HttpGet httpGet = new HttpGet(UrlBuilder.url(baseUrl)
                .addToPath(project)
                .addToPath("master/build.gradle")
                .build());

        try {
            HttpResponse response = httpClient.execute(httpGet);
            String content = ResponseUtils.getContent(response);
            Matcher matcher = PATTERN.matcher(content);
            while (matcher.find()) {
                if (!ignoreList.contains(matcher.group(1))) {
                    result.add(matcher.group(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
