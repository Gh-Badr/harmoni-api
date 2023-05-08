package com.ensias.harmoniAPI.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ensias.harmoniAPI.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	public void deleteByUsername(String username);
	
	
}
