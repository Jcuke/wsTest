package com.tl.mongo.config;


import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tl.mongo.dao2", mongoTemplateRef = "mongoConfig2")
public class MongoConfig2 {

    @Autowired
    @Qualifier("mongoProperties2")
    private MongoProperties mongoProperties;

    @Bean(name = "mongoTemplate2")
    public MongoTemplate mainMongoTemplate() throws Exception {
        return new MongoTemplate(mainFactory(this.mongoProperties));
    }

    public MongoDbFactory mainFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties.getUri()));
    }
}