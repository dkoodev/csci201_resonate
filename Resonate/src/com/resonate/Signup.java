package com.resonate;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username_req = request.getParameter("username");
    		String password_req = request.getParameter("password");
    		String name_req = request.getParameter("name");
    		String email_req = request.getParameter("email");

		Statement st = null;
		PreparedStatement ps = null;
//		ResultSet rs = null;
		
		JDBCDriver.connect();
		try {
		    // username exists, signup success
		    if(JDBCDriver.checkUsernameExists(username_req)) {
		    		session.setAttribute("signupMessage", "Signup Failed");
        			response.sendRedirect("/Resonate/signup.jsp");
		    }
		    // username doesn't exist, signup success
		    else {
				st = JDBCDriver.getConn().createStatement();
			    st.executeUpdate(
			    		"INSERT INTO NonAdminUsers (username, name, password, email)" + 
			    				"VALUES ("
			    					+ "'"+ username_req 		+"',"
			    					+ "'"+ name_req 			+"',"
			    					+ "'"+ password_req 		+"',"
			    					+ "'"+ email_req 		+"'"
			    				+ ");");
		    }
	        
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			try {
//				if (rs != null) {
//					rs.close();
//				}
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
