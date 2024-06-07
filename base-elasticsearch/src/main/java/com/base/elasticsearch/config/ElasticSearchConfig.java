package com.base.elasticsearch.config;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Configuration
public class ElasticSearchConfig  {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Value("${elasticsearch.connTimeout}")
    private Integer connTimeout;

    @Value("${elasticsearch.socketTimeout}")
    private Integer socketTimeout;

    @Value("${elasticsearch.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

//    @Value("${elasticsearch.username}")
//    private String username;
//
//    @Value("${elasticsearch.password}")
//    private String password;

    @Bean(destroyMethod = "close", name = "client")
    public RestHighLevelClient initRestClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(toHttpHost())
                .setRequestConfigCallback(requestConfigBuilder ->
                        requestConfigBuilder.setConnectTimeout(connTimeout)
                                .setSocketTimeout(socketTimeout)
                                .setConnectionRequestTimeout(connectionRequestTimeout));
//                .setHttpClientConfigCallback(h -> h.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
    }


    /**
     * 解析配置的字符串，转为HttpHost对象数组
     */
    private HttpHost[] toHttpHost() {
        if (!StringUtils.hasLength(host)) {
            throw new RuntimeException("invalid elasticsearch configuration");
        }

        String[] hostArray = host.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArray.length];
        HttpHost httpHost;
        for (int i = 0; i < hostArray.length; i++) {
            String[] strings = hostArray[i].split(":");
            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), "http");
            httpHosts[i] = httpHost;
        }
        return httpHosts;
    }
}

