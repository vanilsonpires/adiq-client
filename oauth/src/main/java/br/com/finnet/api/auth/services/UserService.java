package br.com.finnet.api.auth.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.finnet.api.auth.entidy.User;
import br.com.finnet.api.auth.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${finet.admin.username}")
	private String firstUsername;
	
	@Value("${finet.admin.password}")
	private String firstPassword;
	
	@PostConstruct
	public void firstUser() {
		if(userRepository.count()==0L) {
			User user = new User();
			user.setUsername(firstUsername);
			user.setPassword(encoder.encode(firstPassword));
			user.setName("Admin");
			user.setActive(true);
			userRepository.save(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
	public User findIdByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
