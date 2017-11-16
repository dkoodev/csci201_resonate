package com.resonate.objects;

import java.util.HashMap;
import java.util.Vector;

public class Project {
	private int id = -1;
	private int upvoteCount = 0;
	private String name = null;
	private String description = null;
	private String photo = null;
	private String createDate = null;
	private Vector<User> editors = new Vector<User>();
	private Vector<Role> roles = new Vector<Role>();
	private Vector<Track> tracks = new Vector<Track>();
	private Vector<User> contributors = new Vector<User>();
	private Vector<String> tags = new Vector<String>();
	private HashMap<User, Vector<Track>> userToTracks = new HashMap<User, Vector<Track>>();
	
	public Project(int id, int upvoteCount, String name, String description, String photo, String createDate, Vector<User> editors,
			Vector<Role> roles, Vector<Track> tracks, Vector<User> contributors, Vector<String> tags,
			HashMap<User, Vector<Track>> userToTracks) {
		this.id = id;
		this.upvoteCount = upvoteCount;
		this.name = name;
		this.description = description;
		this.setPhoto(photo);
		this.createDate = createDate;
		this.editors = editors;
		this.roles = roles;
		this.tracks = tracks;
		this.contributors = contributors;
		this.tags = tags;
		this.userToTracks = userToTracks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUpvoteCount() {
		return upvoteCount;
	}

	public void setUpvoteCount(int upvoteCount) {
		this.upvoteCount = upvoteCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Vector<User> getEditors() {
		return editors;
	}

	public void setEditors(Vector<User> editors) {
		this.editors = editors;
	}

	public Vector<Role> getRoles() {
		return roles;
	}

	public void setRoles(Vector<Role> roles) {
		this.roles = roles;
	}

	public Vector<Track> getTracks() {
		return tracks;
	}

	public void setTracks(Vector<Track> tracks) {
		this.tracks = tracks;
	}

	public Vector<User> getContributors() {
		return contributors;
	}

	public void setContributors(Vector<User> contributors) {
		this.contributors = contributors;
	}
	
	public Vector<String> getTags(){
		return tags;
	}
	
	public void setTags(Vector<String> tags) {
		this.tags = tags;
	}

	public HashMap<User, Vector<Track>> getUserToTracks() {
		return userToTracks;
	}

	public void setUserToTracks(HashMap<User, Vector<Track>> userToTracks) {
		this.userToTracks = userToTracks;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
	
}
