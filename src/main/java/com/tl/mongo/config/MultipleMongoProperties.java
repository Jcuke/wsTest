package com.tl.mongo.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultipleMongoProperties {

    @Bean(name="mongoProperties")
    @Primary
    @ConfigurationProperties(prefix="spring.data.mongodb")
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Bean(name="mongoProperties2")
    @ConfigurationProperties(prefix="spring.data.mongodb2")
    public MongoProperties mongoProperties2() {
        return new MongoProperties();
    }
}