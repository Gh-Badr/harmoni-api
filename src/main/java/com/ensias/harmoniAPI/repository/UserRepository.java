package com.ensias.harmoniAPI.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ensias.harmoniAPI.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);
	
}
