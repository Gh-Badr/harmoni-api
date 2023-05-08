package com.ensias.harmoniAPI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaylistControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	private String jwt;
	
	@BeforeAll
	public void getJWT() throws Exception {
		if (!(userRepository.existsByUsername("testuser"))) {
	        User user = new User();
	        user.setId("test-id");
	        user.setUsername("testuser");
	        user.setEmail("test@example.com");
	        user.setPassword(passwordEncoder.encode("password"));
	        userRepository.save(user);
	    }
		
		String credentials = "{\"username\": \"testuser\", \"password\": \"password\"}";
    	
        MvcResult result = this.mockMvc.perform(post("/authenticate")
        			.contentType(MediaType.APPLICATION_JSON)
        			.content(credentials))
                .andExpect(status().isOk())
                .andReturn();


        jwt =  result.getResponse().getContentAsString();
        

        
        

	}
	
	@Test
    public void whenCreatePlaylist_thenPlaylistCreated() throws Exception {

		String name = "My Playlist";

        mockMvc.perform(post("/playlists/createPlaylist")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.songs").isEmpty());
    }

}
