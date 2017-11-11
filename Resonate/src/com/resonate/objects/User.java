package com.resonate.objects;

public class User {
	private String username = null;
	private int _id = -1;
	private String name = null;
	private String password = null;
	private String email = null;
	
	public User(int _id, String username, String name, String password, String email) {
		this.username = username;
		this._id = _id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	public User() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String fname) {
		this.name = fname;
	}
	
	public boolean checkPassword(String passwordInput) {
		return password.equals(passwordInput);
	}

	// Made private to restrict access
	private String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
