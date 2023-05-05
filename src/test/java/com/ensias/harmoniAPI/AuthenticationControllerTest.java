package com.ensias.harmoniAPI;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
    private UserRepository userRepository;
	
//	@MockBean
//	private PasswordEncoder passwordEncoder;
	
	@BeforeAll
	public void storeUserInTheDatabase() {
//		given(passwordEncoder.encode(any(CharSequence.class))).willAnswer(invocation -> {
//	        CharSequence rawPassword = invocation.getArgument(0);
//	        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
//	    });

	
		// Create a new user with username "badr" if not existing
		if (userRepository.findByUsername("badr").isEmpty()) {
	        User user = new User();
	        user.setUsername("badr");
	        user.setEmail("test@example.com");
	        user.setPassword("{noop}password");
	        userRepository.save(user);
	    }
		
	}

    @Test
    void root_WhenUnauthenticated_Then401() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser
    public void root_WithMockUser_StatusIsOK() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isOk());
    }
    
    @Test
	void authenticate_WhenWrongCredentials_ThenStatusIsUnauthorized() throws Exception {
    	String credentials = "{\"username\": \"wrongUsername\", \"password\": \"password\"}";
		this.mvc.perform(post("/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(credentials))
		.andExpect(status().isUnauthorized());
	}

    @Test
    
    void root_WhenAuthenticated_ThenSaysHelloUser() throws Exception {
    	
    	String credentials = "{\"username\": \"badr\", \"password\": \"password\"}";
    	
        MvcResult result = this.mvc.perform(post("/authenticate")
        			.contentType(MediaType.APPLICATION_JSON)
        			.content(credentials))
                .andExpect(status().isOk())
                .andReturn();


        
        String token = result.getResponse().getContentAsString();


        this.mvc.perform(get("/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello, badr"));
    }


}
