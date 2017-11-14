package com.resonate;
import java.io.IOException;
import java.util.Vector;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/resonating")
public class ServerSocket {
	
	private static Vector<Session> sessions = new Vector<Session>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("New connection");
		sessions.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(message);
		try {
			for (Session s : sessions) {
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}
	
	@OnClose
	public void close(Session session) {
		sessions.remove(session);
		System.out.println("Connection closed.");
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error: " + error);
	}
}
