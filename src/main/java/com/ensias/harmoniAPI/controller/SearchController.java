package com.ensias.harmoniAPI.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensias.harmoniAPI.service.SearchService;





@RestController
public class SearchController {

	private final SearchService searchService;

	  public SearchController(SearchService searchService) {
	    this.searchService = searchService;
	  }

	  @GetMapping("/search")
	  public ResponseEntity<List<Map<String, Object>>> searchTracks(@RequestParam("q") String query, @RequestParam("limit") int limit) {
		  List<Map<String, Object>> tracks = searchService.searchForTracks(query, limit);
	    return new ResponseEntity<>(tracks, HttpStatus.OK);
	  }
	
	
}
