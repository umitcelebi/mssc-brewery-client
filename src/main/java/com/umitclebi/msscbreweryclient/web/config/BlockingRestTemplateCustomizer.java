package com.umitclebi.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer maxTotalConection;
    private final Integer defaulfMaxPerRoute;
    private final Integer socketTimeOut;
    private final Integer connectionRequestTimeOut;

    public BlockingRestTemplateCustomizer(@Value("${sfg.maxTotalConection}") Integer maxTotalConection,
                                          @Value("${sfg.defaulfMaxPerRoute}") Integer defaulfMaxPerRoute,
                                          @Value("${sfg.socketTimeOut}") Integer socketTimeOut,
                                          @Value("${sfg.connectionRequestTimeOut}") Integer connectionRequestTimeOut) {
        this.maxTotalConection = maxTotalConection;
        this.defaulfMaxPerRoute = defaulfMaxPerRoute;
        this.socketTimeOut = socketTimeOut;
        this.connectionRequestTimeOut = connectionRequestTimeOut;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConection);
        connectionManager.setDefaultMaxPerRoute(defaulfMaxPerRoute);

        RequestConfig config=RequestConfig
                .custom()
                .setConnectionRequestTimeout(connectionRequestTimeOut)
                .setSocketTimeout(socketTimeOut)
                .build();
        CloseableHttpClient httpClient= HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(config)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
