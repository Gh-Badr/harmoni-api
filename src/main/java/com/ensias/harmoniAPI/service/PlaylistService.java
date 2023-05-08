package com.ensias.harmoniAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensias.harmoniAPI.model.Playlist;
import com.ensias.harmoniAPI.repository.PlaylistRepository;

@Service
public class PlaylistService {


    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist createPlaylist(String name, String username) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUsername(username);
        return playlistRepository.save(playlist);
    }

    // Other methods for adding, removing, and getting songs from a playlist

	
}
