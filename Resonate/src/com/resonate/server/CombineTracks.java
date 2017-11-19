package com.resonate.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.resonate.*;
import com.resonate.objects.*;



public class CombineTracks extends Thread {
	Vector<Track> tracks;
	String outputName;
	
	public CombineTracks(Vector<Track> tracks, String out) {
		this.tracks = tracks;
		outputName = out;
		
		this.start();
	}
	
	public void run() {
		ProcessBuilder processBuilder = null;
		Vector<String> cmds = new Vector<String>();
		if (OSValidator.isWindows()) {
			System.out.println("Windows bby");
			cmds.addElement("cmd.exe");
			cmds.addElement("/c");
			cmds.addElement("ffmpeg.exe");
		} else if (OSValidator.isMac() || OSValidator.isUnix()) {
			cmds.addElement("ffmpeg");
		} else {
			System.err.println("OS Not supported to combine tracks.");
			return;
		}
		cmds.addElement("-y");
		for(Track t : tracks) {
			cmds.addElement("-i");
			cmds.addElement(t.getFileLocation());
		}
		cmds.addElement("-filter_complex");
		cmds.addElement("\"[0:0][1:0] amix=inputs=4:duration=longest\"");
		cmds.addElement("-c:a");
		cmds.addElement("libmp3lame");
		cmds.addElement(outputName);
		String command[] = cmds.toArray(new String[cmds.size()]);
		
		File dir;
		
		if (OSValidator.isWindows()) {
			dir = new File("C:/ffmpeg/win/bin");
		} else if (OSValidator.isMac()) {
			dir = new File("/ffmpeg/mac/bin");
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
	}
	
	public static void main(String [] args) {
		Vector<Track> t = new Vector<Track>();
		t.addElement(new Track(null, -1, -1, "Melody.mp3", null, null, null));
		t.addElement(new Track(null, -1, -1, "Instrumental.mp3", null, null, null));
		t.addElement(new Track(null, -1, -1, "Harmony 1.mp3", null, null, null));
		t.addElement(new Track(null, -1, -1, "Harmony 2.mp3", null, null, null));
		
		new CombineTracks(t, "out.mp3");
	}
}
