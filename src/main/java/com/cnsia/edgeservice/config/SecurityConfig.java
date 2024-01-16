package com.cnsia.edgeservice.config;

import org.springframework.context.annotation.Bean;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;



@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(
   ServerHttpSecurity http) {

    return http 
    .authorizeExchange(exchange ->
        exchange.anyExchange().authenticated())
         .formLogin(Customizer.withDefaults())
    .build();

   }

}
