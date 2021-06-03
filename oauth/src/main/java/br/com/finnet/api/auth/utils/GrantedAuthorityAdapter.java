package br.com.finnet.api.auth.utils;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class GrantedAuthorityAdapter implements GrantedAuthority {
	
	private String authority;
	
	public GrantedAuthorityAdapter(String authority){
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}
	
	public static GrantedAuthorityAdapter of(String authority) {
		return new GrantedAuthorityAdapter(authority);
	}

}
