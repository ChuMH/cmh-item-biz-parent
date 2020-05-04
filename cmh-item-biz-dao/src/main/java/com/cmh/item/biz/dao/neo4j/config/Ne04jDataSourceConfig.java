package com.cmh.item.biz.dao.neo4j.config;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = Ne04jDataSourceConfig.MAPPER_PACKAGE )
public class Ne04jDataSourceConfig {


    static final String MAPPER_PACKAGE = "com.cmh.item.biz.dao.neo4j.repository";
    static final String ALIASES_PACKAGE = "com.cmh.item.biz.dao.neo4j.entity";

    @Value("${spring.data.neo4j.uri}")
    private String uri;

    @Value("${spring.data.neo4j..username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean
    public Driver newDriver(){
        return GraphDatabase.driver(uri, AuthTokens.basic(username,password));
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(),Ne04jDataSourceConfig.ALIASES_PACKAGE);
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(uri)
                .credentials(username, password)
                .build();
        return configuration;
    }
    @Bean
    public Neo4jTransactionManager transactionManager() throws Exception {
        return new Neo4jTransactionManager(sessionFactory());
    }
}
