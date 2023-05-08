package com.ensias.harmoniAPI.model;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("playlists")
public class Playlist {
	
	@Id
	private String id;
	
	private String name;
	private String username;
	
    public Playlist() {
        this.songs = new ArrayList<>();
    }
	
	
	// Getters and Setters
	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private List<String> songs;
    


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSongs() {
		return songs;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", songs=" + songs + "]";
	}

	
		
    
    

}
