package com.ensias.harmoniAPI;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchServiceIT {

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
	
	public void compare(MvcResult response, String byWhat, int limit, String query) throws Exception {
		String responseBody = response.getResponse().getContentAsString();
        List<Map<String, Object>> trackList = new ObjectMapper().readValue(responseBody, new TypeReference<List<Map<String,Object>>>(){});
        assertFalse(trackList.isEmpty());
        assertTrue(trackList.size() <= limit);
        assertTrue(trackList.stream().allMatch(track -> ((String) track.get(byWhat)).toLowerCase().contains(query)));
	}
	


	@Test
	public void testSearchForTracksByName() throws Exception {
		
		int limit = 10;
		String query = "breathe";
		
        MvcResult response = mockMvc.perform(get("/search/byName")
				.param("q", query)
				.param("limit", "1")
				.header("Authorization", "Bearer " + jwt))
        		.andExpect(status().isOk())
        		.andReturn();

        this.compare(response,"name",limit,query);
	}
	
	@Test
	public void testSearchForTracksByArtist() throws Exception {
		
		int limit = 10;
		String query = "pink floyd";
		
        MvcResult response = mockMvc.perform(get("/search/byArtist")
				.param("q", query)
				.param("limit", "1")
				.header("Authorization", "Bearer " + jwt))
        		.andExpect(status().isOk())
        		.andReturn();

        this.compare(response,"artist",limit,query);
	}
	
	@Test
	public void testSearchForTracksByAlbum() throws Exception {
		
		int limit = 10;
		String query = "the dark side of the moon";
		
        MvcResult response = mockMvc.perform(get("/search/byAlbum")
				.param("q", query)
				.param("limit", "1")
				.header("Authorization", "Bearer " + jwt))
        		.andExpect(status().isOk())
        		.andReturn();

        this.compare(response,"album",limit,query);
	}
	
	
	
}
