package com.ensias.harmoniAPI;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


@SpringBootTest
@AutoConfigureMockMvc
public class SearchServiceIT {

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Test
	public void testSearchForTracks() throws Exception {
		
		int limit = 10;
		String query = "love";
		
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


        String token = result.getResponse().getContentAsString();

        MvcResult response = mockMvc.perform(get("/search")
				.param("q", query)
				.param("limit", "1")
				.header("Authorization", "Bearer " + token))
        		.andExpect(status().isOk())
        		.andReturn();


        String responseBody = response.getResponse().getContentAsString();
        List<Map<String, Object>> trackList = new ObjectMapper().readValue(responseBody, new TypeReference<List<Map<String,Object>>>(){});
        assertFalse(trackList.isEmpty());
        assertTrue(trackList.size() <= limit);
        assertTrue(trackList.stream().allMatch(track -> ((String) track.get("name")).toLowerCase().contains(query)));
	}
}
