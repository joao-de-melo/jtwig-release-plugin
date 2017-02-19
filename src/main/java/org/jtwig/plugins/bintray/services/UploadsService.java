package org.jtwig.plugins.bintray.services;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.jtwig.plugins.bintray.http.BintrayHttpClient;
import org.jtwig.plugins.bintray.services.model.UploadArtifactRequest;
import org.jtwig.plugins.util.UrlBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadsService {
    private final BintrayHttpClient httpClient;

    public UploadsService(BintrayHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean upload (String baseUrl, UploadArtifactRequest request) {
        UrlBuilder urlBuilder = UrlBuilder.url(baseUrl)
                .addToPath("content")
                .addToPath(request.getPackageVersion().getPath())
                .addToPath(request.getArtifactPath());

        if (request.isOverride()) {
            urlBuilder.addQueryParam("override", "1");
        }

        urlBuilder.addQueryParam("publish", "1");

        HttpPut httpPut = new HttpPut(urlBuilder
                .build());

        httpPut.setHeader("Content-Type", "*/*");
        try (InputStream inputStream = new FileInputStream(request.getFile())) {
            httpPut.setEntity(new ByteArrayEntity(IOUtils.toByteArray(inputStream)));
            HttpResponse response = httpClient.execute(httpPut);
            inputStream.close();
            return response.getStatusLine().getStatusCode() == 201;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
