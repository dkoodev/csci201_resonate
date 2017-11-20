package com.resonate.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.resonate.*;
import com.resonate.objects.*;



public class CombineTracks extends Thread {
	Vector<Track> tracks;
	String outputName;
	
	
	CountDownLatch latch;

	
	public CombineTracks(Vector<Track> tracks, String out, CountDownLatch latch) {
		this.tracks = tracks;
		outputName = Config.destinationPath + out;
		this.latch = latch;
		this.start();

	}
	
	public void run() {

		if(tracks == null) {
			System.out.println("Tracks is null");
			return;
		}
		if(tracks.size() == 0) {
			System.out.println("Tracks is empty");
			return;
		}

		ProcessBuilder processBuilder = null;
		Vector<String> cmds = new Vector<String>();
		if (OSValidator.isWindows()) {
			System.out.println("Windows bby");
			cmds.addElement("cmd.exe");
			cmds.addElement("/c");
			cmds.addElement("ffmpeg.exe");
		} else if (OSValidator.isMac() || OSValidator.isUnix()) {
			cmds.addElement("./ffmpeg");
		} else {
			System.err.println("OS Not supported to combine tracks.");
			return;
		}
		cmds.addElement("-y");
		
		System.out.println("Tracks size: " + tracks.size());
		for(Track t : tracks) {
			if(t == null) {
				System.out.println("t is null");
			}else {
				System.out.println("t is not null");
			}
			if(t.getFileLocation() == null) {
				System.out.println("getfilelocation is null");
			}else {
				System.out.println("getfilelocation is not null");

			}
			System.out.println("t.getFilename : " + t.getFileName());
			System.out.print("t.id: "+ t.getId());

			cmds.addElement("-i");
			String pathToFileLocation = Config.pathToProject + "/Webcontent/" + t.getFileLocation();
			cmds.addElement(pathToFileLocation);
		}
		cmds.addElement("-filter_complex");
		// inputs should depend on the # of files
		String filter = "[0:0][1:0] amix=inputs=" + tracks.size()+ ":duration=longest";
		cmds.addElement(filter);
		cmds.addElement("-c:a");
		cmds.addElement("libmp3lame");
		if(outputName.endsWith(".mp3")) {
			cmds.addElement(outputName);
		}else {
			cmds.addElement(outputName + ".mp3");
		}
		String command[] = cmds.toArray(new String[cmds.size()]);
		
		File dir;
		
		if (OSValidator.isWindows()) {
			dir = new File("C:/ffmpeg/win/bin");
		} else if (OSValidator.isMac()) {
			if(Config.pathToProject.endsWith("/")) {
				dir = new File(Config.pathToProject + "Resources/Resonate/ffmpeg/mac/bin/");
			}else {
				dir = new File(Config.pathToProject + "/Resources/Resonate/ffmpeg/mac/bin/");
			}
		} else if (OSValidator.isUnix()) {
			dir = new File("/ffmpeg/nix/bin");
		} else {
			return;
		}
		
		processBuilder = new ProcessBuilder(command).directory(dir);
        
		try {
		    Process process = processBuilder.start();
		    if( process.getErrorStream().read() != -1 ){
		        System.out.println("Compilation errors: ");
		        InputStream isErr = process.getErrorStream();
		        IOUtils.copy(isErr,System.out);
		        isErr.close();
		    }
		
		    while(process.isAlive()) {
		    	Thread.yield();
		    }
		    if( process.exitValue() == 0 )
		    {
		    	//TODO do something!
		    }
		} catch (IOException ioe) {
			System.out.println("Error in combining tracks thread (ioe):");
			ioe.printStackTrace();
		}

		if(latch !=null) {
			latch.countDown();
		}
	}
	
	public static void main(String [] args) {
		// Test Code
		Vector<Track> t = new Vector<Track>();
		t.addElement(new Track(null, -1, -1, "Melody.mp3", null, null, null));
		t.addElement(new Track(null, -1, -1, "Instrumental.mp3", null, null, null));
		t.addElement(new Track(null, -1, -1, "Harmony 1.mp3", null, null, null));
		t.addElement(new Track(null, -1, -1, "Harmony 2.mp3", null, null, null));
		
		new CombineTracks(t, "out.mp3", null);
	}
}
