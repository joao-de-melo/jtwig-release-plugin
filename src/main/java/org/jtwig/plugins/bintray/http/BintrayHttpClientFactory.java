package org.jtwig.plugins.bintray.http;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jtwig.plugins.bintray.http.auth.BintrayAuthentication;

public class BintrayHttpClientFactory {
    public static BintrayHttpClient create (String baseUrl, BintrayAuthentication authentication) {
        UsernamePasswordCredentials passwordCredentials = new UsernamePasswordCredentials(authentication.getUsername(), authentication.getApiKey());
        return new BintrayHttpClient(createClient(passwordCredentials, null, baseUrl));
    }


    private static CloseableHttpClient createClient(UsernamePasswordCredentials credentials,
                                                    HttpClientConfigurator.ProxyConfig proxyConfig, String url) {

        return new HttpClientConfigurator()
                .hostFromUrl(url)
                .noRetry()
                .proxy(proxyConfig)
                .authentication(credentials)
                .maxTotalConnections(50)
                .defaultMaxConnectionsPerHost(30)
                .getClient();
    }
}
