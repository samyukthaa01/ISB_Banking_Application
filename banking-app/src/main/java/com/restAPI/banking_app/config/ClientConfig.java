package com.restAPI.banking_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class ClientConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("client")
                .clientId("client")
                .clientSecret("{noop}secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("http://localhost:8081/oauth2/authorize")
                .redirectUri("http://localhost:8081/login/oauth2/code/client")
                .tokenUri("http://localhost:8081/oauth2/token") // Set the token URI
                .userInfoUri("http://localhost:8081/oauth2/userinfo") // Set the user info URI
                .scope("read", "write")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}
