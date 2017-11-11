package com.resonate;

import java.sql.*;
import java.util.ArrayList;

public class JDBCDriver {
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	
	private static final String database = "Resonate";
	private static final String password = "root";
	
	public static Connection getConn() {
		return conn;
	}
	
	public static void connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + database + "?user=root&password="+ password +"&useSSL=false");
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
	
	
	public static boolean Login(String usr, String pwd){
		connect();
		try {
			ps = conn.prepareStatement("SELECT password FROM NonAdminUser WHERE username=?");
			ps.setString(1, usr);
			rs = ps.executeQuery();
			System.out.println(rs);
			if(rs.next()){
				if(pwd.equals(rs.getString("password")) ){
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
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
