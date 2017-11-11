package com.resonate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.resonate.objects.Project;
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
		}catch(SQLException sqle){
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}
	
	public static Project getProject(String projectId) {
		connect();
		
		return null;
	}
	
	public static User getUser(String username_req, String password_req) {
		connect();
		int id = -1;
		String username = null;
		String name = null;
		String password = null;
		String email = null;
		String photo = null;
		String bio = null;
		try {
			ps = conn.prepareStatement("SELECT * from NonAdminUsers where username='" + username_req + "' AND password='" + password_req + "'");
		    rs = ps.executeQuery();
		    
			// Get all attributes for user
			id = rs.getInt("_id");
			username = rs.getString("username");
			name = rs.getString("name");
			password = rs.getString("password");
			email = rs.getString("email");
			photo = rs.getString("photo");
			bio = rs.getString("bio");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		// Create instance of user
		User validatedUser = new User(id, username, name, password, email, photo, bio);
		
		return validatedUser;
		
	}
	
	public static void insertUser(String username, String name, String password, String email) {
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
		}
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
		}finally {
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
		}finally{
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
