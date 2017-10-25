package com.resonate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String user = request.getParameter("username");
    	String pw = request.getParameter("password");
    	
    	Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Resonate?user=root&password=root&useSSL=false");
			st = conn.createStatement();
		    rs = st.executeQuery("SELECT * from NonAdminUsers where username='" + user + "' AND password='" + pw + "'");
			//ps = conn.prepareStatement("SELECT * FROM Student WHERE fname=?");
			//ps.setString(1, name); // set first variable in prepared statement
			//rs = ps.executeQuery();

			int id = -1;
			id = rs.getInt("_id");
			String name = rs.getString("name");
			System.out.println(id + " " + name);
			
	        HttpSession session = request.getSession();
	        session.setMaxInactiveInterval(600); // 10 min.
	        if (id != -1) {
	        	request.setAttribute("userid", id); // TODO: Send the user object instead!
	        	response.sendRedirect("/Resonate/user.jsp");
	        } else {
	        	response.sendRedirect("/Resonate/login.jsp");
	        }
	        
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} 	
    }

}
