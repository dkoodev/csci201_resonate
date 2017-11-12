package com.resonate.objects;

import java.util.HashMap;
import java.util.Vector;

public class Project {
	private int id = -1;
	private int upvoteCount = 0;
	private String name = null;
	private String description = null;
	private 	String createDate = null;
	private Vector<User> editors = new Vector<User>();
	private Vector<Track> tracks = new Vector<Track>();
	private Vector<User> contributors = new Vector<User>();
	private HashMap<User, Role> userToRole = new HashMap<User, Role>();
	private HashMap<Role, Vector<Track>> roleToTracks = new HashMap<Role, Vector<Track>>();
	
	
	
	
}
