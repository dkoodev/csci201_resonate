package com.resonate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.resonate.objects.Project;
import com.resonate.objects.Role;
import com.resonate.objects.Track;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.resonate.objects.User;

public class JDBCDriver {
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	
	private static final String host = "sql3.freemysqlhosting.net";
	private static final String database = "sql3204487";
	private static final String password = "dq7vgwQD4T";
	private static final String user = "sql3204487";
	
	public static Connection getConn() {
		return conn;
	} 
	
	public static boolean connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ host +"/" + database + "?user=" + user + "&password="+ password +"&useSSL=false");
		} catch (ClassNotFoundException e) {
			System.out.println("Error connecting to database (cnfe): " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error connecting to database (sqle): " + e.getMessage());
			e.printStackTrace();
		}
		
		if(conn == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public static void close(){
		try{
			if (rs!=null){
				rs.close();
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
			if(ps != null ){
				ps = null;
			}
			
			// For whatever reason, jdbc is not closing this thread and tomcat is not happy.
			AbandonedConnectionCleanupThread.uncheckedShutdown();
		} catch(SQLException sqle) {
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}
	
	public static int getTrackVotes(int project_id) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return -1;
		}
		
		try {
			ps = conn.prepareStatement(
					"SELECT * from Track"
					+ "WHERE project_id = " + project_id + ""
					+ ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		int upvoteCount = rs.getInt("upvoteCount");
		    	
		    		return upvoteCount;
		    } else {
			    	close();
			    	return -1;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return -1;
		} finally {
			close();
		}
		
	}
	
	public static boolean updateTrackVote(int project_id, int track_id) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}

		try {
			ps = conn.prepareStatement(
					"UPDATE Tracks "
					+ "SET 	upvoteCount = upvotecount + 1 "
					+ "WHERE project_id=" + project_id + ""
						+ "AND track_id=" + track_id + ";"
					);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		} finally { // will this run..?
			close();
		}
		
		return true;
	}
		
	public static int getProjectLikes(int project_id) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return -1;
		}
		
		try {
			ps = conn.prepareStatement(
					"SELECT * from LikedProjects"
					+ "WHERE project_id = " + project_id + ""
					+ ";");
		    rs = ps.executeQuery();
		    int likeCount = 0;
		    if(rs.next()) {
		    		likeCount = 0;
		    		do {
		    			likeCount++;
		    		} while(rs.next());
		    		return likeCount;
		    } else {
			    	close();
			    	return -1;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return -1;
		} finally {
			close();
		}
		
	}
	
	public static boolean insertProjectLike(int project_id, int user_id) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}

		try {
			ps = conn.prepareStatement(
					"UPDATE Projects "
					+ "SET 	upvoteCount = upvotecount + 1 "
					+ "WHERE project_id=" + project_id + ";"
					);
			ps.executeUpdate();
			
			if(!checkUserLikedProjectAlready(project_id, user_id)) {
				ps = conn.prepareStatement(
						"INSERT INTO LikedProjects (project_id, user_id)" + 
								"VALUES ("
									+ 	 	project_id		+","
									+  		user_id			
								+ ");"
						);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		} finally { // will this run..?
			close();
		}
		
		return true;
	}
	
	public static boolean checkUserLikedProjectAlready(int project_id, int user_id) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}

		try {
			ps = conn.prepareStatement(
					"SELECT * from LikedProjects"
					+ "WHERE project_id = " + project_id
					+ "AND user_id = " + user_id
					+ ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		return true;
		    } else {
			    	close();
			    	return false;
		    }
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		} finally { // will this run..?
			close();
			return false;
		}
	}

	
	public static Track insertTrack(
							String name, int upvoteCount, String fileLocation, 
							String fileName, Integer delay, 
							User currentUser, Project currentProject, 
							Integer role_id){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		try {
			// Inserting project into table
			ps = conn.prepareStatement(
					"INSERT INTO Tracks (name, project_id, role_id, user_id, upvoteCount, fileLocation, fileName, delay)" + 
							"VALUES ("
								+ "'"+  name						+"',"
								+ 	 	currentProject.getId()  	+","
								+ 		role_id  				+","
								+ 		currentUser.get_id()  	+","
								+		upvoteCount				+","
								+ "'"+  fileLocation  			+"',"
								+ "'"+  fileName  				+"',"
								+  		delay  					
							+ ");"
					);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(
					"SELECT * Tracks "
					+ "WHERE name='"+ name +"'"
					+ "AND project_id=" + currentProject.getId()
					+ "AND role_id=" + role_id
					+ "AND user_id=" + currentUser.get_id()
					+ "AND fileLocation='" + fileLocation +"'"
					+ "AND fileName='" + fileName +"'"
					+ "AND delay=" + delay
					+";"
					);
			rs = ps.executeQuery();

			if(rs.next()) {
				int new_track_id = -1;
				String new_track_name = null;
				int new_track_project_id = -1;
				int new_track_role_id = -1;
				int new_track_user_id = -1;
				String new_track_fileLocation = null;
				String new_track_fileName = null;
				Integer new_track_delay = null;
				
				do {
					new_track_id = rs.getInt("_id");
					new_track_name = rs.getString("name");
					new_track_project_id = rs.getInt("project_id");
					new_track_role_id = rs.getInt("role_id");
					new_track_user_id = rs.getInt("user_id");
					new_track_fileLocation = rs.getString("fileLocation");
					new_track_fileName = rs.getString("fileName");
					new_track_delay = rs.getInt("delay");
				} while(rs.next());
				
				User creator = getUserById(new_track_user_id, false);
					
				Track track = new Track(new_track_name, new_track_id, 0, new_track_fileLocation, new_track_fileName, new_track_delay, creator);
				
				insertContributor(creator, new_track_project_id, role_id);
				
				return track;
			}else {
				close();
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return null;
		} finally { // will this run..?
			close();
		}
		
	}

	// TODO: complete this function
	public static boolean updateUser(User user) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}
		
		try {
			int _id = user.get_id();
			String username = user.getUsername();
			String name = user.getName();
			String password = user.getPassword();
			String email = user.getEmail();
			String photo = user.getPhoto();
			String bio = user.getBio();
			
			ps = conn.prepareStatement(
					"UPDATE NonAdminUsers "
					+ "SET 	username = '"	+ username 	+ "',"
					+ "		name = '" 		+ name 		+ "',"
					+ "		password = '" 	+ password	+ "',"
					+ "		email = '"		+ email		+ "',"
					+ "		photo = '"		+ photo 		+ "',"
					+ "		bio = '"			+ bio		+ "'"
					+ "WHERE _id="+ _id + ";"
					);
					
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;

		} finally { // will this runn..?
			close();
		}
		return true;
	}
	
	public static boolean insertContributor(User contributor, int project_id, int role_id) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}

		try {
			// Inserting project into table
			ps = conn.prepareStatement(
					"INSERT INTO Contributors (project_id, user_id, role_id)" + 
							"VALUES ("
								+ 	 	project_id				+","
								+  		contributor.get_id()   	+","
								+ 		role_id 
							+ ");"
					);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		} finally { // will this run..?
			close();
		}
		
		return false;
	}
	
	public static Project createProject(String projectName, String projectDescription, String projectGenre, String projectPhoto, Vector<String> projectResources, User creator) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		if(creator == null) {
			return null;
		}

		
		Project project = null;
		//System.out.println("Inserting project.");
		try {
			// Inserting project into table
			ps = conn.prepareStatement(
					"INSERT INTO Projects (name, description, genre, upvoteCount, photo)" + 
							"VALUES ("
								+ "'"+ projectName 			+"',"
								+ "'"+ projectDescription 	+"',"
								+ "'"+ projectGenre			+"',"
								+ "0,"
								+ "'" + projectPhoto + "'"
							+ ");"
					);
			ps.executeUpdate();
			
			System.out.println("Getting inserted project");
			// Verifying/ Getting project id
			ps = conn.prepareStatement(
					"SELECT * from Projects"
					+ " WHERE name = '" + projectName + "'"
					+ " AND description = '" + projectDescription + "'"
					+ "	AND genre = '" + projectGenre + "'"
					+ " AND upvoteCount =0;");
		    rs = ps.executeQuery();
			
		    int project_id = -1;
		    if(rs.next()) {
		    		project_id = rs.getInt("_id");
		    } else {
		    	System.out.println("no table.");
		    	close();
		    	return null;
		    }
		    //System.out.println("Got project" + project_id);
		    
		    if (project_id == -1) {
		    	close();
		    	return null;
		    }
		    
		    //System.out.println("Creator id: " + creator.get_id());
		    // Inserting project and user relationship to Editors
			ps = conn.prepareStatement(
					"INSERT INTO Editors (project_id, user_id)" + 
							" VALUES ("
								+ project_id 		+","
								+ creator.get_id()
							+ ");"
					);
			ps.executeUpdate();
			
		    // Inserting project into user's 'my projects'
			ps = conn.prepareStatement(
					"INSERT INTO MyProjects (project_id, user_id)" + 
							" VALUES ("
								+ project_id 		+","
								+ creator.get_id()
							+ ");"
					);
			ps.executeUpdate();
			
			//TODO: Update resources.. 
			
			project = getProject(project_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return null;
		} finally { // will this run..?
			close();
		}
		return project;
	}
	
	public static Vector<Project> getProjects(){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<Project> projects = new Vector<Project>();
		
		try {  
			// Getting project information
			ps = conn.prepareStatement("SELECT * from Projects;");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int upvoteCount = 0;
		    			int project_id = -1;
		    			String project_name = null;
		    			String project_description = null;
		    			String project_genre = null;
		    			String project_photo = null;
		    			String createDate = null;
		    			
		    			project_id = rs.getInt("_id");
			    		project_name = rs.getString("name");
			    		project_description = rs.getString("description");
			    		project_genre = rs.getString("genre");
			    		project_photo = rs.getString("photo");
			    		upvoteCount = rs.getInt("upvoteCount");
			    		createDate = rs.getString("createDate");

					    Project project = new Project(project_id, upvoteCount, project_name, project_description,project_genre, project_photo, createDate, null, null, null, null, null, null);
					    projects.add(project);
		    		} while(rs.next());

		    } else {
		    		close();
		    		return null;
		    }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return null;
		} finally {
			close();
		}
		
		return projects;
	}
	
	public static Project getProject(int projectId) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		int upvoteCount = 0;
		String project_name = null;
		String project_description = null;
		String project_genre = null;
		String project_photo = null;
		String createDate = null;
		Vector<User> editors = new Vector<User>();
		Vector<User> contributors = new Vector<User>();
		Vector<Track> tracks = new Vector<Track>();
		Vector<Role> roles = new Vector<Role>();
		HashMap<User, Vector<Track>> userToTracks = new HashMap<User, Vector<Track>>();

		Project project = null;
		
		try {  
			// Getting project information
			ps = conn.prepareStatement("SELECT * from Projects where _id=" + projectId + ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    	project_name = rs.getString("name");
		    	project_description = rs.getString("description");
		    	project_genre = rs.getString("genre");
		    	upvoteCount = rs.getInt("upvoteCount");
		    	createDate = rs.getString("createDate");
		    } else {
		    	close();
		    	return null;
		    }
		    
		    editors = getEditorsByProjectId(projectId);

		    contributors = getContributorsByProjectId(projectId);
		    
		    tracks = getTracksByProjectId(projectId);
		    System.out.println("tracks: " + tracks);
		    
		    roles = getRolesByProjectId(projectId);
		    
		    if  (contributors != null) {
		    	userToTracks = getUserToTracksByProjectId_Contributors(projectId, contributors);
		    }

		    // Add in tags 
		    
		    project = new Project(projectId, upvoteCount, project_name, project_description, project_genre, project_photo, createDate, editors, roles, tracks, contributors, null, userToTracks);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return null;
		} finally {
			close();
		}
		
		return project;
	}
	
	public static HashMap<User, Vector<Track>> getUserToTracksByProjectId_Contributors(int projectId, Vector<User> contributors){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		HashMap<User, Vector<Track>> userToTracks = new HashMap<User, Vector<Track>>();
		
		for(User contributor: contributors) {
			Vector<Track> tracksOfContributor = new Vector<Track>();
			userToTracks.put(contributor, tracksOfContributor);
		}

		close();
		
		return userToTracks;
	}
	
	public static Vector<Track> getTracksByProjectId_UserId(int projectId, int userId){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<Track> tracks = new Vector<Track>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Tracks"
					+ "WHERE project_id = " + projectId + ""
					+ "AND user_id = " + userId + ""
					+ ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
	    		do {
	    			int id = rs.getInt("_id");
	    			String name = rs.getString("name");
	    			int upvoteCount = rs.getInt("upvoteCount");
	    			String fileLocation = rs.getString("fileLocation");
	    			String fileName = rs.getString("fileName");
	    			int delay = rs.getInt("delay");
	    			//int user_id = rs.getInt("t.user_id");
	    					
	    			User creator = getUserById(userId, true); //(user_id);
	    			Track track = new Track(name, id, upvoteCount, fileLocation, fileName, delay, creator);

	    			tracks.add(track);
	    		} while(rs.next());
		    } else {
		    	close();
		    	return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return null;
		} finally {
			close();
		}
		
		return tracks;
	}
	
	public static Vector<Role> getRolesByProjectId(int projectId){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<Role> roles = new Vector<Role>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Roles "
					+ "WHERE project_id = " + projectId + ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("_id");
		    			String name = rs.getString("name");
		    			String description = rs.getString("description");
		    			Vector<Track> tracks = getTracksByRoleId(id);

		    			Role role = new Role(id, name, description, tracks);
		    			roles.add(role);
		    		} while(rs.next());
		    } else {
		    	
		    		close();
		    	return roles;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return roles;
		} finally {
			
				close();
		}
		
		return roles;
	}
	
	public static Vector<Track> getTracksByRoleId(int roleId){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<Track> tracks = new Vector<Track>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Tracks "
					+ "WHERE role_id = " + roleId + ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("_id");
		    			String name = rs.getString("name");
		    			int upvoteCount = rs.getInt("upvoteCount");
		    			String fileLocation = rs.getString("fileLocation");
		    			String fileName = rs.getString("fileName");
		    			int delay = rs.getInt("delay");
		    			int user_id = rs.getInt("user_id");
		    					
		    			User creator = getUserById(user_id, true);
		    			Track track = new Track(name, id, upvoteCount, fileLocation, fileName, delay, creator);

		    			tracks.add(track);
		    		} while(rs.next());
		    } else {
		    	
		    		close();
		    	return tracks;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
				close();
			return tracks;
		} finally {
			
				close();
		}
		
		return tracks;
	}
	
	public static Vector<Track> getTracksByProjectId(int projectId){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<Track> tracks = new Vector<Track>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Tracks "
					+ "WHERE project_id=?");
			ps.setInt(1, projectId);
		    rs = ps.executeQuery();
		    System.out.println("Query executed.");
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("_id");
		    			String name = rs.getString("name");
		    			int upvoteCount = rs.getInt("upvoteCount");
		    			String fileLocation = rs.getString("fileLocation");
		    			String fileName = rs.getString("fileName");
		    			int delay = rs.getInt("delay");
		    			int user_id = rs.getInt("user_id");
		    					
		    			User creator = getUserById(user_id, true);
		    			Track track = new Track(name, id, upvoteCount, fileLocation, fileName, delay, creator);

		    			tracks.add(track);
		    			System.out.println("Track Made: " + track.getName());
		    		} while(rs.next());
		    } else {
		    	System.out.println("Returning " + tracks.size() + " tracks.");
		    	return tracks;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
				close();
		}
		
		return tracks;
	}
	
	public static Vector<User> getEditorsByProjectId(int projectId){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<User> editors = new Vector<User>();
		try {
		    // Getting list of editors
			//ps = conn.prepareStatement("SELECT * from Editors e, NonAdminUsers u where e.project_id= u._id AND e.project_id = '" + projectId + "'");
			ps = conn.prepareStatement("SELECT u._id as userid," +
											" u.username, u.name, u.email, u.photo, u.bio" +
											" from Editors e, NonAdminUsers u where e.project_id= u._id AND e.project_id = " + projectId + "");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("userid");
		    			String username = rs.getString("username");
		    			String name = rs.getString("name");
		    			String password = null;
		    			String email = rs.getString("email");
		    			String photo = rs.getString("photo");
		    			String bio = rs.getString("bio");
		    			
		    			User editor = new User(id, username, name, password, email, photo, bio);
		    			
		    			editors.add(editor);
		    		} while(rs.next());
		    } else {
		    		return editors;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return editors;
		} finally {
			
				close();
		}
		return editors;
	}
	
	public static Vector<User> getContributorsByProjectId(int projectId){
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		Vector<User> contributors = new Vector<User>();
		try {
		    // Getting list of contributors
			ps = conn.prepareStatement("SELECT u._id as userid," + 
											" u.username, u.name, u.email, u.photo, u.bio from" + 
											" Contributors c, NonAdminUsers u where c.project_id= u._id AND c.project_id = " + projectId + "");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("userid");
		    			String username = rs.getString("username");
		    			String name = rs.getString("name");
		    			String password = null;
		    			String email = rs.getString("email");
		    			String photo = rs.getString("photo");
		    			String bio = rs.getString("bio");
		    			
		    			User contributor = new User(id, username, name, password, email, photo, bio);
		    			
		    			contributors.add(contributor);
		    		} while(rs.next());
		    } else {
		    	
		    		close();
		    	return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			
				close();
		}
		return contributors;
	}

	// For whatever reason, this gets called inside other resultsets. It needs its own.
	public static User getUserById(int user_id, boolean altRS) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		int id = user_id;
		String username = null;
		String name = null;
		String password = null;
		String email = null;
		String photo = null;
		String bio = null;
		
		try {
			ps = conn.prepareStatement("SELECT * from NonAdminUsers where _id=" + id + ";");
			if (altRS) {
				ResultSet rs2 = ps.executeQuery();
				 if(rs2.next()) {
				     do { // should only be one row, but needed or sqle
						id = rs2.getInt("_id");
						username = rs2.getString("username");
						name = rs2.getString("name");
						password = null;
						email = rs2.getString("email");
						photo = rs2.getString("photo");
						bio = rs2.getString("bio");
				    } while (rs2.next());
			    } else {
			    	rs2.close();
			    	return null;
			    }
				 rs2.close();
			} else {
			    rs = ps.executeQuery();
			    
			    if(rs.next()) {
				     do { // should only be one row, but needed or sqle
						id = rs.getInt("_id");
						username = rs.getString("username");
						name = rs.getString("name");
						password = null;
						email = rs.getString("email");
						photo = rs.getString("photo");
						bio = rs.getString("bio");
				    } while (rs.next());
			    } else {
			    	
			    		close();
			    	return null;
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (!altRS)
				close();
			return null;
		} finally {
			if (!altRS)
				close();
		}
		// Create instance of user
		User user = new User(id, username, name, password, email, photo, bio);
		
		return user;
	}
	
	public static User getUser(String username_req, String password_req) throws SQLException {
		if(!connect()) {
			System.out.println("Not connected to database");
			return null;
		}
		
		int id = -1;
		String username = null;
		String name = null;
		String password = null;
		String email = null;
		String photo = null;
		String bio = null;
		try {
			ps = conn.prepareStatement("SELECT * from NonAdminUsers where username='" + username_req + "'"
													+ " and password='" + password_req + "';");
		    rs = ps.executeQuery();
		    
			// Get all attributes for user
		    if(rs.next()) {
			    do { // should only be one row, but needed or sqle
					id = rs.getInt("_id");
					username = rs.getString("username");
					name = rs.getString("name");
					password = rs.getString("password");
					email = rs.getString("email");
					photo = rs.getString("photo");
					bio = rs.getString("bio");
			    }while (rs.next()) ;
		    }else {
		    		return null;
		    }

		} catch (SQLException e) {
			throw e;
		} finally {
			close();
		}
		// Create instance of user
		User validatedUser = new User(id, username, name, password, email, photo, bio);
		
		return validatedUser;
	}
	
	public static boolean insertUser(String username, String name, String password, String email) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}
		
		try {
			ps = conn.prepareStatement(
					"INSERT INTO NonAdminUsers (username, name, password, email)" + 
							"VALUES ("
								+ "'"+ username 		+"',"
								+ "'"+ name 			+"',"
								+ "'"+ password 		+"',"
								+ "'"+ email		+"'"
							+ ");"
					);
					
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;

		} finally { // will this runn..?
			close();
		}
		return true;
	}

	public static boolean checkEmailExists(String email) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}
		
		try {
			ps = conn.prepareStatement("SELECT username FROM NonAdminUsers WHERE email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()){
				close();
				return true;
			}
		} catch(SQLException e) {
			System.out.println("SQLException in checkEmailExists(String email) ");
			e.printStackTrace();
		}finally {
			close();
		}
		
		return false;
	}
	
	public static boolean checkUsernameExists(String username) {
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}
		
		try {
			ps = conn.prepareStatement("SELECT username FROM NonAdminUsers WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()){
				close();
				return true;
			}
		} catch(SQLException e) {
			System.out.println("SQLException in checkUsernameExists(String username) ");
			e.printStackTrace();
		} finally {
			close();
		}
		
		return false;
	}
	
	
	public static boolean login(String usr, String pwd){
		if(!connect()) {
			System.out.println("Not connected to database");
			return false;
		}
		
		try {
			ps = conn.prepareStatement("SELECT password FROM NonAdminUsers WHERE username=?");
			ps.setString(1, usr);
			rs = ps.executeQuery();
			if(rs.next()){
				if(pwd.equals(rs.getString("password")) ){
					close();
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException in login(String usr, String pwd)");
			e.printStackTrace();
		} finally {
			close();
		}
		return false;	
	}
	
	public static Vector<Project> getProjectsByUserId(int id) {
		Vector<Project> projects = new Vector<Project>();
		if(!connect()) {
			System.out.println("Not connected to database");
			return projects;
		}
		int proj_id;
		int proj_votes;
		String proj_name;
		String proj_desc;
		String proj_genre;
		String proj_photo;
		String proj_createDate;
		// TODO: the rest of the project stuff.
		try {
			ps = conn.prepareStatement("SELECT e._id as EditorID, p.* from Editors e, Projects p where e.user_id=? and p._id=e.project_id;");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				proj_id = rs.getInt("_id");
				proj_votes = rs.getInt("upvoteCount");
				proj_name = rs.getString("name");
				proj_desc = rs.getString("description");
				proj_genre = rs.getString("genre");
				proj_photo = rs.getString("photo");
				proj_createDate = rs.getString("createDate");
				Project p = new Project(proj_id, proj_votes, proj_name, proj_desc, proj_genre, proj_photo, proj_createDate, null, null, null, null, null, null);
				projects.add(p);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProjectByUserId");
			e.printStackTrace();
		} finally {
			close();
		}
		
		return projects;
	}

	public static Vector<Project> getLikedProjectsByUserId(int id) {
		Vector<Project> projects = new Vector<Project>();
		if(!connect()) {
			System.out.println("Not connected to database");
			return projects;
		}
		int proj_id;
		int proj_votes;
		String proj_name;
		String proj_desc;
		String proj_genre;
		String proj_photo;
		String proj_createDate;
		// TODO: the rest of the project stuff.
		try {
			ps = conn.prepareStatement("SELECT l._id as LProjID, p.* from LikedProjects l, Projects p where l.user_id=? and p._id=l.project_id;");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				proj_id = rs.getInt("_id");
				proj_votes = rs.getInt("upvoteCount");
				proj_name = rs.getString("name");
				proj_desc = rs.getString("description");
				proj_genre = rs.getString("genre");
				proj_photo = rs.getString("photo");
				proj_createDate = rs.getString("createDate");
				Project p = new Project(proj_id, proj_votes, proj_name, proj_desc,proj_genre, proj_photo, proj_createDate, null, null, null, null, null, null);
				projects.add(p);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProjectByUserId");
			e.printStackTrace();
		} finally {
			close();
		}
		
		return projects;
	}
}