package com.resonate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resonate.objects.Track;
import com.resonate.server.CombineTracks;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    	String project_id = request.getParameter("projectid");
	    	String numTracks = request.getParameter("numTracks");
	    	System.out.println("project_id from downloadservlet is: " + project_id);
//	    	int pId = Integer.parseInt(project_id);
	    	int nTracks = Integer.parseInt(numTracks);
	    	
	    	//Vector<String> trackDelays = new Vector<String>();
	    	
	    	Vector<Integer> tracksToDownloadById = new Vector<Integer>();
	    	
	    	String tracksString = "";
	    	for (int i = 0; i < nTracks; i++) {
	    		String temp = request.getParameter("track_" + i);
	    		int track_id = Integer.parseInt(temp);
	    		tracksToDownloadById.add(track_id);
	    		if(i != 0) {
	    			tracksString += "-";
	    		}
	    		tracksString += track_id;
	    	}
	    	
	    	Vector<Track> tracks = new Vector<Track>();

	    	for(Integer i : tracksToDownloadById) {
	    		Track track = JDBCDriver.getTrackById(i);
	    		if(track == null) {
	    			System.out.println("track was null in Downloadservlet getTrackById. Track id: " + i);
	    		}else {
	    			System.out.println("track was not null in Downloadservlet getTrackById. Track id: " + i);
		    		tracks.add(track);
	    		}
	    	}
	    	
	    	String fileName = "project_"+project_id+"_tracks_"+numTracks+"_"+tracksString;
	    	
	    	CountDownLatch latch = new CountDownLatch(1);

	    	System.out.println("Countdownlatch created");
	    	System.out.println("CombineTracks started");
		new CombineTracks(tracks, fileName, latch);
    		System.out.println("CombineTracks ended");
	    	try {
	    		latch.await();
	    	} catch (InterruptedException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	System.out.println("After latch.await()");
	    	
	    	if(!fileName.endsWith(".mp3")) {
	    		fileName += ".mp3";
	    	}
	    	
        File file = new File(Config.pathToProject + "/WebContent/mergedTracks/", fileName);
        response.setHeader("Content-Type", getServletContext().getMimeType(fileName));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
	}


}
