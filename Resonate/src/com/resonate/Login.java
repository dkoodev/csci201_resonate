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

		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		JDBCDriver.connect();
		try {
			st = JDBCDriver.getConn().createStatement();
		    rs = st.executeQuery("SELECT * from NonAdminUsers where username='" + user + "' AND password='" + pw + "'");

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
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} 	
    }

}
