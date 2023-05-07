package com.ensias.harmoniAPI;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ensias.harmoniAPI.model.User;
import com.ensias.harmoniAPI.repository.UserRepository;
import com.ensias.harmoniAPI.service.SearchService;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SearchService searchService;
	
    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void whenSearchForTracks_thenReturnsTrackList() throws Exception {
		
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
        
        
		
		Map<String, Object> trackMap = new HashMap<>();
		trackMap.put("name", "Track 1");
		trackMap.put("artist", "Artist 1");
		trackMap.put("album", "Album 1");
		trackMap.put("images", Arrays.asList("Image 1", "Image 2"));
		trackMap.put("uri", "URI 1");

		List<Map<String, Object>> trackList = Arrays.asList(trackMap);

		when(searchService.searchForTracks(anyString(), anyInt())).thenReturn(trackList);

		mockMvc.perform(get("/search")
				.param("q", "search query")
				.param("limit", "1")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Track 1"))
				.andExpect(jsonPath("$[0].artist").value("Artist 1"))
				.andExpect(jsonPath("$[0].album").value("Album 1"))
				.andExpect(jsonPath("$[0].images[0]").value("Image 1"))
				.andExpect(jsonPath("$[0].images[1]").value("Image 2"))
				.andExpect(jsonPath("$[0].uri").value("URI 1"));
	}
}