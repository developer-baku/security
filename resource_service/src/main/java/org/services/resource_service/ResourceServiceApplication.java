package org.services.resource_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class ResourceServiceApplication {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.oauth2ResourceServer(c->c.opaqueToken(opaqueTokenConfigurer ->
            opaqueTokenConfigurer.introspectionUri("http//localhost:8082/oauth2/introspect")
                    .introspectionClientCredentials("db", "secret")
                ))
                .authorizeHttpRequests(a->a.anyRequest().authenticated())

                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceApplication.class, args);
    }

}
