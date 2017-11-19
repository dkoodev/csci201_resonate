package com.resonate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.resonate.objects.Project;
import com.resonate.objects.Role;
import com.resonate.objects.Track;
import com.resonate.objects.User;


/**
 * Servlet implementation class CreateTrack
 */
@WebServlet("/CreateTrack")
public class CreateTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTrack() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		Project currentProject = (Project) session.getAttribute("project");
		
		// Values from form
		String track_name = request.getParameter("");
		String track_fileLocation = null;
		String track_fileName = null;
		Integer track_delay = -1;
		Integer role_id = -1;
		
		Track track = new Track(track_name, -1, 0, track_fileLocation, track_fileName, track_delay, currentUser);
		
		if(JDBCDriver.insertTrack(track_name, 0, track_fileLocation, track_fileName, track_delay, currentUser, currentProject, role_id) != null)  {
			// TODO: Track successfully added | Maybe go to auditionStage?
		}else {
			// TODO: Track did not get added for some reason. |redirectback?
		}
		
	}



}
