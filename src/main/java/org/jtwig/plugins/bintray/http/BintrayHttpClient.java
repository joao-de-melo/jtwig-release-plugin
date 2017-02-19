package org.jtwig.plugins.bintray.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public class BintrayHttpClient {
    private final HttpClient httpClient;

    public BintrayHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse execute (HttpRequestBase request) {
        try {
            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException("Unable to execute request "+request, e);
        }
    }
}
