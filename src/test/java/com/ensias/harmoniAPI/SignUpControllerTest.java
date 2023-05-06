package com.ensias.harmoniAPI;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

	@Autowired
	MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;



    @Test
    public void whenSignupValidUser_thenReturnOk() throws Exception {
    	
    	if(userRepository.existsByUsername("testuser")) userRepository.deleteByUsername("testuser");

        String requestBody = "{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        		.andExpect(status().isOk())
        		.andExpect(content().string("User created successfully"))
        		.andReturn();

    }

    @Test
    public void whenSignupUserWithExistingUsername_thenReturnBadRequest() throws Exception {
    	
    	
		if (!(userRepository.existsByUsername("testuser"))) {
	        User user = new User();
	        user.setUsername("testuser");
	        user.setEmail("test@example.com");
	        user.setPassword(passwordEncoder.encode("password"));
	        userRepository.save(user);
	    }
        
        
		String requestBody = "{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password\"}";
		
		
        this.mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        		.andExpect(status().isBadRequest())
        		.andExpect(content().string("Username already exists"))
        		.andReturn();

    }
}
