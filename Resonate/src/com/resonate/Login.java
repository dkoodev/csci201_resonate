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
        session.setMaxInactiveInterval(600); // 10 min.

        if (JDBCDriver.login(username_req, password_req)) {
			User validatedUser = JDBCDriver.getUser(username_req, password_req);
			
        		request.setAttribute("user", validatedUser); 
        		response.sendRedirect("/Resonate/user.jsp");
        } else {
    			session.setAttribute("loginMessage", "Login Failed");
        		response.sendRedirect("/Resonate/login.jsp");
        }
    }
}