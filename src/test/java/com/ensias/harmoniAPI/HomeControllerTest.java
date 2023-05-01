package com.ensias.harmoniAPI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ensias.harmoniAPI.config.SecurityConfig;
import com.ensias.harmoniAPI.controller.HomeController;
import com.ensias.harmoniAPI.controller.AuthenticationController;
import com.ensias.harmoniAPI.service.TokenService;


@WebMvcTest({ HomeController.class, AuthenticationController.class })
@Import({ SecurityConfig.class, TokenService.class })
public class HomeControllerTest {
	
	@Autowired
	MockMvc mvc;

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
    	String credentials = "{\"username\": \"wrongUserName\", \"password\": \"password\"}";
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
