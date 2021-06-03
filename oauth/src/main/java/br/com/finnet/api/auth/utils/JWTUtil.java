package br.com.finnet.api.auth.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
	
	@Autowired
	private  JwtTokenStore tokenStore;

	public Map<String, Object> recoveryUsername(String token) {
		String[] tokenSplit = token.split(" ");
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenSplit[1]);
		return accessToken.getAdditionalInformation();
	}

}
