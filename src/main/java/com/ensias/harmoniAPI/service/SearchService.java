package com.ensias.harmoniAPI.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
public class SearchService {
	
	
	@Autowired
	private SpotifyApi spotifyApi;

	public List<Map<String, Object>> searchForTracksByName(String query, int limit) {
		SearchTracksRequest request = spotifyApi.searchTracks(query).limit(limit).build();
		return executeRequest(request);
	}
		
	public List<Map<String, Object>> searchForTracksByArtist(String query, int limit) {
		SearchTracksRequest request = spotifyApi.searchTracks("artist:" + query).limit(limit).build();
		return executeRequest(request);
	}
		
	public List<Map<String, Object>> searchForTracksByAlbum(String query, int limit) {
	    SearchTracksRequest request = spotifyApi.searchTracks("album:" + query).limit(limit).build();
	    return executeRequest(request);
	}
		
	private List<Map<String, Object>> executeRequest(SearchTracksRequest request) {
	    try {
	      	Paging<Track> tracks = request.execute();
	      	Track[] trackArray = tracks.getItems();
	      	List<Track> trackList = Arrays.asList(trackArray);
	      	return trackList.stream().map((Track track) -> {
	      		Map<String, Object> trackMap = new HashMap<>();
	      		trackMap.put("name", track.getName());
	      		trackMap.put("artist", track.getArtists()[0].getName());
	      		trackMap.put("album", track.getAlbum().getName());
	      		trackMap.put("images", track.getAlbum().getImages());
	      		trackMap.put("uri", track.getUri());
	      		return trackMap;
	      	}).collect(Collectors.toList());
	    } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
	      	e.printStackTrace();
	      	return Collections.emptyList();
	    }
	}
}
