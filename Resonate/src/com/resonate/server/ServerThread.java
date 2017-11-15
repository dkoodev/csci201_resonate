package com.resonate.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Server server;

	
	public ServerThread(Socket s, Server server) {
		try {
			this.server = server;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			while(true) {
				// Receiving string from client and passing on to the server
				String message = (String)ois.readObject();
				server.response(message, this);
			}
		} catch (IOException ioe) {
			System.out.println("Server has terminated");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
}
