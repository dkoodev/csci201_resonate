package com.resonate.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server{
	private Vector<ServerThread> serverThreads;
	
	public Server(int port) throws IOException{

		try{
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Successfully started the server on port " + port);
			serverThreads = new Vector<ServerThread>();
			while(true){
				Socket s = ss.accept();
//				System.out.println("Connection from: " + s.getInetAddress() );
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
				response(null, st);
			}
		} catch(IOException ioe){
			throw new IOException(ioe);
		}
	}
	
	public void response(String message, ServerThread st) {
		
	}

	public static void main(String[] args) {
		Server s = null;
		
		System.out.println("Starting server...");
		
		try {
			s = new Server(8080);
		}catch (IOException ioe) {
			System.out.println("Invalid port number");
		}catch (NumberFormatException nfe) {
			System.out.println("Invalid port number");
		}
	}
}