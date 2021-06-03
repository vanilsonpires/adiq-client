package br.com.finnet.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ApiCloudServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCloudServerApplication.class, args);
	}

}
