package com.coex.backend.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration
{
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	@Value("${spring.datasource.user}")
	private String dbUser;
	@Value("${spring.datasource.host}")
	private String dbHost;
	@Value("${spring.datasource.port}")
	private Integer dbPort;
	
    @Bean
    public RestClient getRestClient() {
    	final CredentialsProvider credentialsProvider =
    		    new BasicCredentialsProvider();
    		credentialsProvider.setCredentials(AuthScope.ANY,
    		    new UsernamePasswordCredentials(dbUser, dbPassword));
        RestClient restClient = RestClient.builder(
                new HttpHost(dbHost, dbPort)).setHttpClientConfigCallback(new HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();
        return restClient;
    }

    @Bean
    public  ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }


    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }

}