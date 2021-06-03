package br.com.finnet.api.auth.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

	public static List<String> getOAuthTokenScopes() {
		return Collections.unmodifiableList(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList()));
	}

}
