package com.resonate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.resonate.objects.User;

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

	    // username exists, signup success
	    if(JDBCDriver.checkUsernameExists(username_req)) {
	    		session.setAttribute("signupMessage", "Signup Failed");
    			response.sendRedirect("/Resonate/signup.jsp");
	    }
	    // username doesn't exist, signup success
	    else {
	    		JDBCDriver.insertUser(username_req, name_req, password_req, email_req);
	    		User user = JDBCDriver.getUser(username_req, password_req);
	    		Mailer.UserJoinedEmail(username_req, name_req, email_req);
	    		
	    		response.sendRedirect("/Resonate/login.jsp?signup=" + username_req);
	    }

    }

}
