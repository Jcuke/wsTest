package com.tl.wsTest;

import com.tl.mongo.services.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.tl.**"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class StartApp implements CommandLineRunner {

	@Autowired
	DataUtil dataUtil;

	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//dataUtil.loop();
	}
}

