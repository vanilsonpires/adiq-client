package br.com.finnet.api.auth.entidy;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.finnet.api.auth.utils.GrantedAuthorityAdapter;
import lombok.Data;

@SuppressWarnings("serial")
@Entity(name = "users")
public @Data class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, columnDefinition = "text")
	private String username;

	@Column(nullable = false, columnDefinition = "text")
	private String name;

	@Column(nullable = false, columnDefinition = "text")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean active;
	
	@Override
	public List<GrantedAuthorityAdapter> getAuthorities() {
		//FIXME: create roles authorities....		
		return null;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
}
