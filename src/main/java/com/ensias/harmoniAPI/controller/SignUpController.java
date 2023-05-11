package com.ensias.harmoniAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.repository.UserRepository;

@RestController
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class SignUpController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {
		
		if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    	
		User userCreated = new User();
        userCreated.setUsername(user.getUsername());
        userCreated.setEmail(user.getEmail());
        userCreated.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userCreated);
        
        return ResponseEntity.ok("User created successfully");
		
    }
	
	
}
