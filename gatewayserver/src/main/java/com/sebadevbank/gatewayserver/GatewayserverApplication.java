package com.sebadevbank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator sebadevBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/sebadevbank/accounts/**")
						.filters(f -> f.rewritePath("/sebadevbank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())) //conf de fallback
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/sebadevbank/transactions/**")
						.filters(f -> f.rewritePath("/sebadevbank/transactions/(?<segment>.*)", "/${segment}")
//								.addRequestHeader("X-User-Id","#{principal?.attributes['sub']}")
//								.addRequestHeader("X-User-Roles", "#{principal?.authorities}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())) // solo para metodo GET
						.uri("lb://TRANSACTIONS"))
				.route(p -> p
						.path("/sebadevbank/cards/**")
						.filters(f -> f.rewritePath("/sebadevbank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS")).build();
	}

}
