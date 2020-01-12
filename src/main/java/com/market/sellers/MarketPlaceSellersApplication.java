package com.market.sellers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableDiscoveryClient
@SpringBootApplication
public class MarketPlaceSellersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPlaceSellersApplication.class, args);
	}

}
