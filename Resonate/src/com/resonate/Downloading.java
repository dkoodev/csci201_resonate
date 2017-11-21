package com.resonate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.resonate.objects.Project;

/**
 * Servlet implementation class Downloading
 */
@WebServlet("/Downloading")
public class Downloading extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Downloading() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		HttpSession session = request.getSession();
		File file = (File) session.getAttribute("fileToDownload");
		String fileName = (String) session.getAttribute("fileToDownloadFileName");
        response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
        
        Project p = (Project)session.getAttribute("project");
		response.sendRedirect("Resonate/auditionstage.jsp?project=" + p.getId());
	}


}
