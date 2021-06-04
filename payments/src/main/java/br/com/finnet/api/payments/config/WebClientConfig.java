package br.com.finnet.api.payments.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Configuration
@SuppressWarnings("deprecation")
public class WebClientConfig {	
	
	@Value("${adiq.url}")
	private String url;
	
	@Bean
    ReactiveClientRegistrationRepository getRegistration(
            @Value("${spring.security.oauth2.client.provider.adiq.token-uri}") String tokenUri,
            @Value("${spring.security.oauth2.client.registration.adiq.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.adiq.client-secret}") String clientSecret,
            @Value("${spring.security.oauth2.client.registration.adiq.scopes}") String scope
    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("adiq")
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(scope)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

	@Bean(name = "adiq")
	WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {		
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = 
				new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrations, new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
		oauth.setDefaultClientRegistrationId("adiq");
		return WebClient.builder()
				.baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.filter(oauth)
				.filter(logRequest()).build();
	}
	
	private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: [{}] {}", clientRequest.method(), clientRequest.url());
            log.debug("Payload: {}", clientRequest.body());
            return Mono.just(clientRequest);
        });
    }

}
