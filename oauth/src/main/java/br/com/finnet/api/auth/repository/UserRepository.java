package br.com.finnet.api.auth.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.finnet.api.auth.entidy.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByUsernameIgnoreCase(String username);
	
	@Cacheable(value = "UserRepository.findIdByUsername")
	public Long findIdByUsername(String username);

}
