package com.ensias.harmoniAPI.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("songs")
public class Song {

	@Id
	private String id;
	
	private String name;
    private String artist;
    private String album;
    private String uri;
    private List<Image> images;
    
    public static class Image {
        private int height;
        private int width;
        private String url;

        public Image() {
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    
    
    
    // Getters and Setters

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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", artist=" + artist + ", album=" + album + ", uri=" + uri
				+ ", images=" + images + "]";
	}


    
    
	
	
	
}
