package com.resonate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.resonate.objects.Project;
import com.resonate.objects.Role;
import com.resonate.objects.Track;
import com.resonate.objects.User;


/**
 * Servlet implementation class CreateTrack
 */
@WebServlet("/CreateTrack")
@MultipartConfig
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
		User creator = (User)request.getSession().getAttribute("user");
		if (creator == null) {
			session.setAttribute("errorMessage", "Not Logged In");
			response.sendRedirect("/Resonate/createproject.jsp");
		}
		Project currentProject = (Project) session.getAttribute("project");

		// Values from form
		String track_name = request.getParameter("name");
		String track_fileLocation = null;
		String track_fileName = null;
		Integer track_delay = -1;
		Integer role_id = 1;
 
		// Multipart file thingy
		String root = Config.pathToProject + "/WebContent/uploads";
		String filename;

		Part filePart = request.getPart("track"); 

		if (filePart != null && !filePart.getSubmittedFileName().equals("")) {
			filename = filePart.getSubmittedFileName();

			File path = new File(root + "/tracks/project" + currentProject.getId() );
			if (!path.exists()) {
				boolean status = path.mkdirs();
			}
			
			track_fileLocation = "uploads/tracks/project" + currentProject.getId() + "/" + filename;
			track_fileName = filename;
			File uploadedFile = new File(path + "/" + filename);

			try (InputStream input = filePart.getInputStream()) {
				Files.copy(input, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}

			System.out.println("File uploaded to: " + uploadedFile.toPath());
		}

		Track track = JDBCDriver.insertTrack(track_name, 0, track_fileLocation, track_fileName, track_delay, creator, currentProject, role_id);

		if(track != null)  {
			// TODO: Track successfully added | Maybe go to auditionStage?
			session.setAttribute("trackAdded", track);
			response.sendRedirect("auditionstage.jsp?project=" + currentProject.getId());
		}else {
			// TODO: Track did not get added for some reason. |redirectback?
			session.setAttribute("error", "track was not added");
			response.sendRedirect("auditionstage.jsp?project=" + currentProject.getId());
		}

	}



}
