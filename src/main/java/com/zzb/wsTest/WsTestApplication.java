package com.zzb.wsTest;

import com.zzb.mongo.services.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.zzb.**"})
@SpringBootApplication
public class WsTestApplication implements CommandLineRunner {

	@Autowired
	DataUtil dataUtil;

	public static void main(String[] args) {
		SpringApplication.run(WsTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataUtil.loop();
	}
}

