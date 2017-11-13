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
	
	public static void connect(){
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
	}
	
	public static void close(){
		try{
			if (rs!=null){
				rs.close();
				rs = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
			if(ps != null ){
				ps = null;
			}
			
			// For whatever reason, jdbc is not closing this thread and tomcat is not happy.
			//AbandonedConnectionCleanupThread.checkedShutdown(); // .shutdown();
			AbandonedConnectionCleanupThread.uncheckedShutdown();
		} catch(SQLException sqle) {
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}

	public static boolean updateUser(User user) {
		
		return false;
	}
	
	public static boolean insertContributors(User contributor, Project project) {
		connect();

		try {
			// Inserting project into table
			ps = conn.prepareStatement(
					"INSERT INTO Contributors (project_id, user_id, role_id)" + 
							"VALUES ("
								+ "'"+  project.getId()			+"',"
								+ "'"+  contributor.get_id()   	+"',"
								+ "0"
							+ ");"
					);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally { // will this run..?
			close();
		}
		
		return false;
	}
	
	public static Project createProject(String projectName, String projectDescription, Vector<String> projectResources, User creator) {
		connect();
		Project project = null;
		if(creator == null) {
			return null;
		}
		try {
			// Inserting project into table
			ps = conn.prepareStatement(
					"INSERT INTO Projects (name, description, upvoteCount)" + 
							"VALUES ("
								+ "'"+ projectName 			+"',"
								+ "'"+ projectDescription 	+"',"
								+ "0"
							+ ");"
					);
			ps.executeUpdate();
			
			// Verifying/ Getting project id
			ps = conn.prepareStatement(
					"SELECT * from Projects"
					+ "WHERE name = '" + projectName + "'"
					+ "AND projectDescription = '" + projectDescription + "'"
					+ "AND upvoteCount = " + 0 + ""
					+ ";");
		    rs = ps.executeQuery();
			
		    int project_id = -1;
		    if(rs.next()) {
		    		project_id = rs.getInt("_id");
		    }else {
		    		return null;
		    }
		    
		    // Inserting project and user relationship to Editors
			ps = conn.prepareStatement(
					"INSERT INTO Editors (project_id, user_id)" + 
							"VALUES ("
								+ project_id 		+","
								+ creator.get_id() 	+","
								+ "0"
							+ ");"
					);
			ps.executeUpdate();
			
		    // Inserting project and user relationship to Editors
			ps = conn.prepareStatement(
					"INSERT INTO MyProjects (project_id, user_id)" + 
							"VALUES ("
								+ project_id 		+","
								+ creator.get_id() 	+","
								+ "0"
							+ ");"
					);
			ps.executeUpdate();
			
			//TODO: Update resources.. 
			
			project = getProject(project_id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally { // will this run..?
			close();
		}
		return project;
	}
	
	public static Vector<Project> getProjects(){
		Vector<Project> projects = new Vector<Project>();
		
		connect();
		
		try {  
			// Getting project information
			ps = conn.prepareStatement("SELECT * from Projects ;");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int upvoteCount = 0;
		    			int project_id = -1;
		    			String project_name = null;
		    			String project_description = null;
		    			String createDate = null;
		    			
		    			project_id = rs.getInt("_id");
			    		project_name = rs.getString("name");
			    		project_description = rs.getString("description");
			    		upvoteCount = rs.getInt("upvoteCount");
			    		createDate = rs.getString("createDate");

				    Project project = new Project(project_id, upvoteCount, project_name, project_description, createDate, null, null, null, null, null, null);
				    projects.add(project);
		    		}while(rs.next());

		    }else {
		    		return null;
		    }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
		return projects;
	}
	
	public static Project getProject(int projectId) {
		connect();
		
		int upvoteCount = 0;
		String project_name = null;
		String project_description = null;
		String createDate = null;
		Vector<User> editors = new Vector<User>();
		Vector<User> contributors = new Vector<User>();
		Vector<Track> tracks = new Vector<Track>();
		Vector<Role> roles = new Vector<Role>();
		HashMap<User, Vector<Track>> userToTracks = new HashMap<User, Vector<Track>>();

		Project project = null;
		
		try {  
			// Getting project information
			ps = conn.prepareStatement("SELECT * from Projects where _id='" + projectId + "';");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		project_name = rs.getString("name");
		    		project_description = rs.getString("description");
		    		upvoteCount = rs.getInt("upvoteCount");
		    		createDate = rs.getString("createDate");
		    }else {
		    		return null;
		    }
		    
		    editors = getEditorsByProjectId(projectId);

		    contributors = getContributorsByProjectId(projectId);
		    
		    tracks = getTracksByProjectId(projectId);
		    
		    roles = getRolesByProjectId(projectId);
		    
		    userToTracks = getUserToTracksByProjectId_Contributors(projectId, contributors);

		    // Add in tags 
		    
		    project = new Project(projectId, upvoteCount, project_name, project_description, createDate, editors, roles, tracks, contributors, null, userToTracks);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
		return project;
	}
	
	public static HashMap<User, Vector<Track>> getUserToTracksByProjectId_Contributors(int projectId, Vector<User> contributors){
		connect();
		HashMap<User, Vector<Track>> userToTracks = new HashMap<User, Vector<Track>>();
		
		for(User contributor: contributors) {
			Vector<Track> tracksOfContributor = new Vector<Track>();
			userToTracks.put(contributor, tracksOfContributor);
		}

		close();
		
		return userToTracks;
	}
	
	public static Vector<Track> getTracksByProjectId_UserId(int projectId, int userId){
		connect();
		Vector<Track> tracks = new Vector<Track>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Track t"
					+ "WHERE t.project_id = '" + projectId + "'"
					+ "AND t.user_id = '" + userId + "'"
					+ ";");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("t._id");
		    			String name = rs.getString("t.name");
		    			String fileLocation = rs.getString("t.");
		    			String fileName = rs.getString("t.fileName");
		    			int delay = rs.getInt("t.delay");
		    			int user_id = rs.getInt("t.user_id");
		    					
		    			User creator = getUserById(user_id);
		    			Track track = new Track(name, id, fileLocation, fileName, delay, creator);

		    			tracks.add(track);
		    		}while(rs.next());
		    }else {
		    		return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
		return tracks;
	}
	
	public static Vector<Role> getRolesByProjectId(int projectId){
		connect();
		Vector<Role> roles = new Vector<Role>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Roles r"
					+ "WHERE r.project_id = '" + projectId + "';");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("r._id");
		    			String name = rs.getString("r.name");
		    			String description = rs.getString("r.description");
		    			Vector<Track> tracks = getTracksByRoleId(id);

		    			Role role = new Role(id, name, description, tracks);
		    			roles.add(role);
		    		}while(rs.next());
		    }else {
		    		return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
		return roles;
	}
	
	public static Vector<Track> getTracksByRoleId(int roleId){
		connect();
		Vector<Track> tracks = new Vector<Track>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Track t"
					+ "WHERE t.role_id = '" + roleId + "';");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("t._id");
		    			String name = rs.getString("t.name");
		    			String fileLocation = rs.getString("t.");
		    			String fileName = rs.getString("t.fileName");
		    			int delay = rs.getInt("t.delay");
		    			int user_id = rs.getInt("t.user_id");
		    					
		    			User creator = getUserById(user_id);
		    			Track track = new Track(name, id, fileLocation, fileName, delay, creator);

		    			tracks.add(track);
		    		}while(rs.next());
		    }else {
		    		return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
		return tracks;
	}
	
	public static Vector<Track> getTracksByProjectId(int projectId){
		
		connect();
		Vector<Track> tracks = new Vector<Track>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement(
					"SELECT * from Track t"
					+ "WHERE t.project_id = '" + projectId + "';");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("t._id");
		    			String name = rs.getString("t.name");
		    			String fileLocation = rs.getString("t.");
		    			String fileName = rs.getString("t.fileName");
		    			int delay = rs.getInt("t.delay");
		    			int user_id = rs.getInt("t.user_id");
		    					
		    			User creator = getUserById(user_id);
		    			Track track = new Track(name, id, fileLocation, fileName, delay, creator);

		    			tracks.add(track);
		    		}while(rs.next());
		    }else {
		    		return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
		return tracks;
	}
	
	public static Vector<User> getEditorsByProjectId(int projectId){
		connect();
		Vector<User> editors = new Vector<User>();
		try {
		    // Getting list of editors
			ps = conn.prepareStatement("SELECT * from Editors e, NonAdminUsers u where e.project_id= u._id AND e.project_id = '" + projectId + "'");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("u._id");
		    			String username = rs.getString("u.username");
		    			String name = rs.getString("u.name");
		    			String password = null;
		    			String email = rs.getString("u.email");
		    			String photo = rs.getString("u.photo");
		    			String bio = rs.getString("u.bio");
		    			
		    			User editor = new User(id, username, name, password, email, photo, bio);
		    			
		    			editors.add(editor);
		    		}while(rs.next());
		    }else {
		    		return null;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		return editors;
	}
	
	public static Vector<User> getContributorsByProjectId(int projectId){
		connect();
		Vector<User> contributors = new Vector<User>();
		try {
		    // Getting list of contributors
			ps = conn.prepareStatement("SELECT * from Contributors c, NonAdminUsers u where c.project_id= u._id AND c.project_id = '" + projectId + "'");
		    rs = ps.executeQuery();
		    if(rs.next()) {
		    		do {
		    			int id = rs.getInt("u._id");
		    			String username = rs.getString("u.username");
		    			String name = rs.getString("u.name");
		    			String password = null;
		    			String email = rs.getString("u.email");
		    			String photo = rs.getString("u.photo");
		    			String bio = rs.getString("u.bio");
		    			
		    			User contributor = new User(id, username, name, password, email, photo, bio);
		    			
		    			contributors.add(contributor);
		    		}while(rs.next());
		    }else {
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

	public static User getUserById(int user_id) {
		connect();
		
		int id = user_id;
		String username = null;
		String name = null;
		String password = null;
		String email = null;
		String photo = null;
		String bio = null;
		
		try {
			ps = conn.prepareStatement("SELECT * from NonAdminUsers where _id='" + id + "';");
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
		    }else {
		    		return null;
		    }

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		// Create instance of user
		User user = new User(id, username, name, password, email, photo, bio);
		
		return user;
	}
	
	public static User getUser(String username_req, String password_req) throws SQLException {
		connect();
		
		int id = -1;
		String username = null;
		String name = null;
		String password = null;
		String email = null;
		String photo = null;
		String bio = null;
		try {
			ps = conn.prepareStatement("SELECT * from NonAdminUsers where _id='" + id + "';");
		    rs = ps.executeQuery();
		    
			// Get all attributes for user
		    while (rs.next()) { // should only be one row, but needed or sqle
				id = rs.getInt("_id");
				username = rs.getString("username");
				name = rs.getString("name");
				password = rs.getString("password");
				email = rs.getString("email");
				photo = rs.getString("photo");
				bio = rs.getString("bio");
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
		connect();
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
			return false;

		} finally { // will this runn..?
			close();
		}
		return true;
	}

	public static boolean checkEmailExists(String email) {
		connect();
		
		try {
			ps = conn.prepareStatement("SELECT username FROM NonAdminUsers WHERE email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()){
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
		connect();
		try {
			ps = conn.prepareStatement("SELECT username FROM NonAdminUsers WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()){
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
		connect();
		try {
			ps = conn.prepareStatement("SELECT password FROM NonAdminUsers WHERE username=?");
			ps.setString(1, usr);
			rs = ps.executeQuery();
			if(rs.next()){
				if(pwd.equals(rs.getString("password")) ){
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
	
	/*
	public static ArrayList<ArrayList<String> >getData(){
		ArrayList<ArrayList<String>>  stat = new ArrayList<ArrayList<String>>();
		connect();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Lab10?user=root&password=root&useSSL=false");
			ps = conn.prepareStatement("SELECT u.username, p.name, pv.count FROM User u, Page p, PageVisited pv "
					+ "WHERE u.userID = pv.userID AND p.pageID = pv.pageID ORDER BY u.userID, p.pageID");
			rs = ps.executeQuery();
			while(rs.next()){
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("u.username"));
				row.add(rs.getString("p.name"));
				row.add(rs.getString("pv.count"));
				stat.add(row);
			}
		}catch(SQLException sqle){
			System.out.println("SQLException in function \" getData\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
		return stat;
	}
	*/
}
