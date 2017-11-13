package com.resonate;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.resonate.objects.Project;

/**
 * Servlet implementation class BrowseProjects
 */
@WebServlet("/BrowseProjects")
public class BrowseProjects extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Vector<Project> projects = JDBCDriver.getProjects();
        
        session.setAttribute("projects", projects);
		
	}

}
