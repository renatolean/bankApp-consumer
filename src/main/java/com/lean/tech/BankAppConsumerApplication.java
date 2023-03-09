package com.lean.tech;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class BankAppConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAppConsumerApplication.class, args);
	}

	@Bean
	@Qualifier("bankA-service")
	WebClient bankAServiceWebClient(WebClient.Builder webClientBuilder, @Value("${bankA-service.url}") String baseUrl) {
		return webClientBuilder
				.baseUrl(baseUrl)
				.build();
	}
}
