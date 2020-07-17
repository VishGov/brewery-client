package com.vishnu.breweryclient.config;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Setter
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final int maxTotal;
    private final int maxPerRoute;
    private final int connectionRequestTimeout;
    private final int socketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${rest-template.maxTotal}") int maxTotal,
                                          @Value("${rest-template.maxPerRoute}")int maxPerRoute,
                                          @Value("${rest-template.connectionRequestTimeout}")int connectionRequestTimeout,
                                          @Value("${rest-template.socketTimeout}")int socketTimeout) {
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
