package com.resonate.objects;

public class Track {
	String name = null;
	int id = -1;
	String fileLocation = null;
	String fileName = null;
	Integer delay = null;
	public Track(String name, int id, String fileLocation, String fileName, Integer delay) {
		this.name = name;
		this.id = id;
		this.fileLocation = fileLocation;
		this.fileName = fileName;
		this.delay = delay;
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
	

}
