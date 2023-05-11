package com.ensias.harmoniAPI.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensias.harmoniAPI.service.SearchService;





@RestController
@RequestMapping("/search")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class SearchController {

	private final SearchService searchService;

	  public SearchController(SearchService searchService) {
	    this.searchService = searchService;
	  }

	  @GetMapping("/byName")
		public ResponseEntity<List<Map<String, Object>>> searchTracksByName(@RequestParam("q") String query,
				@RequestParam("limit") int limit) {
			List<Map<String, Object>> tracks = searchService.searchForTracksByName(query, limit);
			return new ResponseEntity<>(tracks, HttpStatus.OK);
		}

		@GetMapping("/byArtist")
		public ResponseEntity<List<Map<String, Object>>> searchTracksByArtist(@RequestParam("q") String query,
				@RequestParam("limit") int limit) {
			List<Map<String, Object>> tracks = searchService.searchForTracksByArtist(query, limit);
			return new ResponseEntity<>(tracks, HttpStatus.OK);
		}

		@GetMapping("/byAlbum")
		public ResponseEntity<List<Map<String, Object>>> searchTracksByAlbum(@RequestParam("q") String query,
				@RequestParam("limit") int limit) {
			List<Map<String, Object>> tracks = searchService.searchForTracksByAlbum(query, limit);
			return new ResponseEntity<>(tracks, HttpStatus.OK);
		}
	
	
}
