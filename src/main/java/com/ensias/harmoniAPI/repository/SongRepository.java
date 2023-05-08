package com.ensias.harmoniAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ensias.harmoniAPI.model.Song;

public interface SongRepository extends MongoRepository<Song, String> {

	
	
}
