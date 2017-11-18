package com.resonate.objects;

public class Track {
	String name = null;
	int id = -1;
	int upvoteCount = 0;
	String fileLocation = null;
	String fileName = null;
	Integer delay = null;
	User creator = null;
	
	public Track(String name, int id, int upvoteCount, String fileLocation, String fileName, Integer delay, User creator) {
		this.name = name;
		this.id = id;
		this.upvoteCount = upvoteCount;
		this.fileLocation = fileLocation;
		this.fileName = fileName;
		this.delay = delay;
		this.creator = creator;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public int getVotes() {
		return upvoteCount;
	}
	public void setVotes(int upv) {
		this.upvoteCount = upv;
	}

}
