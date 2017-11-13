package com.resonate.objects;

import java.util.Vector;

public class Role {
	int id = -1;
	String name = null;
	String description = null;
	Vector<Track> tracks = new Vector<Track>();
	
	public Role(int id, String name, String description, Vector<Track> tracks) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.tracks = tracks;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return name;
	}
	public void setType(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Vector<Track> getTracks(){
		return tracks;
		
	}
	public void setTracks(Vector<Track> tracks) {
		this.tracks = tracks;
	}
	
	
	
}
