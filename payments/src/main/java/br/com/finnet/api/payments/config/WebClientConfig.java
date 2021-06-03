package br.com.finnet.api.payments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@SuppressWarnings("deprecation")
@Configuration
public class WebClientConfig {

	@Bean
	WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = 
				new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrations, new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
		oauth.setDefaultClientRegistrationId("adiq");
		return WebClient.builder().filter(oauth).build();
	}

}
