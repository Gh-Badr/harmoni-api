package com.ensias.harmoniAPI.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.service.TokenService;

@RestController
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class AuthenticationController {
	
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
    private AuthenticationManager authManager;

    @PostMapping("/authenticate")
    public String token(@RequestBody User user) {
    	try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            System.out.println("Authentication successful");
            return tokenService.generateToken(authentication);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e;
        }
        
    }

}
