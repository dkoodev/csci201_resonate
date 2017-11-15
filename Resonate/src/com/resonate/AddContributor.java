package com.resonate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.resonate.objects.Project;
import com.resonate.objects.User;

/**
 * Servlet implementation class AddContributor
 */
@WebServlet("/AddContributor")
public class AddContributor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddContributor() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        User contributor = (User) session.getAttribute("user");
        Project project = (Project) session.getAttribute("project");
        // File file = file?!?!?
        
        // File ? Resource?
        
        // Maybe scrap this java? Because adding the track to the project should automatically add the user to the project as a contributor
        if(JDBCDriver.insertContributor(contributor, project.getId(),0)) {
        		
        }else {
        	
        }
	}
	
	

}
