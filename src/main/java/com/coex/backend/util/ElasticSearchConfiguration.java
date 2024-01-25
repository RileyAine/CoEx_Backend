package com.coex.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfiguration extends ElasticsearchConfiguration
{
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	@Value("${spring.datasource.user}")
	private String dbUser;
	@Value("${spring.datasource.host}")
	private String dbHost;
	@Value("${spring.datasource.port}")
	private Integer dbPort;
	
	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()           
				.connectedTo(dbHost + ":" + dbPort).withBasicAuth(dbUser, dbPassword)
				.build();
	}
	
 
}