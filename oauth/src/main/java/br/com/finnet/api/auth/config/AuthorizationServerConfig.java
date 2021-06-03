package br.com.finnet.api.auth.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Value("${security.user.name}")
	private String name;
	
	@Value("${security.user.password}")
	private String password;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private JwtTokenStore tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient(name)
			.secret(passwordEncoder.encode(password))
			.authorizedGrantTypes("authorization_code", "refresh_token", "password").scopes("all")
			.accessTokenValiditySeconds((int)Duration.ofMinutes(15).getSeconds())
			.refreshTokenValiditySeconds((int)Duration.ofDays(1).getSeconds())
		.and();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter);
	}
	
}
