package com.resonate;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.resonate.objects.User;

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
    	String username_req = request.getParameter("username");
    	String password_req = request.getParameter("password");
        HttpSession session = request.getSession();

        User validatedUser = null;
        if (JDBCDriver.login(username_req, password_req)) {
        	try {
        		validatedUser = JDBCDriver.getUser(username_req, password_req);
        	} catch (SQLException sqle) {
        		sqle.printStackTrace();
        		session.setAttribute("errorMessage", "SQL Error");
            	response.sendRedirect("/Resonate/login.jsp");
            	return;
        	}

        	session.setMaxInactiveInterval(600); // 10 min.
        	session.setAttribute("user", validatedUser); 
        	response.sendRedirect("/Resonate/user.jsp");
        } else {
        	session.setMaxInactiveInterval(30); // 30s.
    		session.setAttribute("errorMessage", "Login Failed");
        	response.sendRedirect("/Resonate/login.jsp");
        }
    }
}