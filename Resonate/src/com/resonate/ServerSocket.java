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
		String messageParts[] = message.split(" ");
		String type = messageParts[0];
		int thing_id = Integer.parseInt(messageParts[1]);
		int user_proj_id = -1;
		if (messageParts[2] != null && !messageParts[2].equals("-1")) {
			user_proj_id = Integer.parseInt(messageParts[2]);
		}

		if (type.equals("p")) {
			if (user_proj_id != -1) {
				
				JDBCDriver.insertProjectLike(thing_id, user_proj_id);
			}
		} else if (type.equals("t")) {
			if (user_proj_id != -1) {
				JDBCDriver.updateTrackVote(user_proj_id, thing_id);
			}
		}
		
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
		error.printStackTrace();
		System.out.println("Error: " + error);
	}
}
