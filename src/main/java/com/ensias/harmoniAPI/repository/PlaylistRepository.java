package com.ensias.harmoniAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ensias.harmoniAPI.model.Playlist;

public interface PlaylistRepository extends MongoRepository<Playlist, String> {

}
