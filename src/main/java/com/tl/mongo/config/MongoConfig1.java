package com.tl.mongo.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tl.mongo.dao", mongoTemplateRef = "mongoConfig1")
public class MongoConfig1 {

    @Autowired
    @Qualifier("mongoProperties")
    private MongoProperties mongoProperties;

    @Primary
    @Bean(name = "mongoTemplate")
    public MongoTemplate mngoTemplate() throws Exception {
        return new MongoTemplate(mainFactory(this.mongoProperties));
    }

    public MongoDbFactory mainFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties.getUri()));
    }
}