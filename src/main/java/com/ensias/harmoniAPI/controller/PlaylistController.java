package com.ensias.harmoniAPI.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensias.harmoniAPI.model.Playlist;
import com.ensias.harmoniAPI.service.PlaylistService;

@RestController
@RequestMapping("/playlists")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class PlaylistController {

	@Autowired
    private PlaylistService playlistService;

	@PostMapping("/createPlaylist")
	public ResponseEntity<?> createPlaylist(@RequestParam String name, Principal principal) {
	    String username = principal.getName();
	    Playlist playlist = playlistService.createPlaylist(name, username);
	    return ResponseEntity.ok(playlist);
	}

    // Other endpoints for adding, removing, and getting songs from a playlist

	
}
