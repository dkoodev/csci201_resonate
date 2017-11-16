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
 * Servlet implementation class UpdateAccount
 */
@WebServlet("/UpdateAccount")
public class UpdateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		// File if 
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String photo = request.getParameter("photo");
		String bio = request.getParameter("bio");
		
		if(		JDBCDriver.checkUsernameExists(username) ||
				username.equals("") ||
				name.equals("") ||
				password.equals("") ||
				JDBCDriver.checkEmailExists(email) ||
				email.equals("") ||
				photo.equals("") ||
				bio.equals("")
				) {
			// Redirect to accounts page with failed message
			session.setAttribute("error", "Fields are not valid");
			response.sendRedirect("/updateProfile.jsp");
			return;
		}else {
			user.setName(name);
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhoto(photo);
			user.setBio(bio);
			
			if(JDBCDriver.updateUser(user)) {
				user = JDBCDriver.getUserById(user.get_id());
				session.setAttribute("user", user);
				
			}else {
				
			}
		}
	}

}
