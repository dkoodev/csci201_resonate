package com.resonate;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveProject
 */
@WebServlet("/SaveProject")
public class SaveProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveProject() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String project_id = request.getParameter("projectid");
    	String numTracks = request.getParameter("numTracks");
    	int pId = Integer.parseInt(project_id);
    	int nTracks = Integer.parseInt(numTracks);
    	
    	//Vector<String> trackDelays = new Vector<String>();
    	
    	for (int i = 0; i < nTracks; i++) {
    		String temp = request.getParameter("track_" + i);
    		//trackDelays.add(e)
    		String comp[] = temp.split(" ");
    		int trackId = Integer.parseInt(comp[0]);
    		int tDelay = Integer.parseInt(comp[1]);
    		
    		boolean f = JDBCDriver.saveDelay(trackId, tDelay);
    		if (!f) System.out.println("Failure");
    	}
    	response.sendRedirect("/Resonate/auditionstage.jsp?project=" + pId);
	}

}
