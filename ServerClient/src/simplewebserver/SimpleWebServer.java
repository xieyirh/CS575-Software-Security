package simplewebserver;

/****************************************************************
SimpleWebServer.java
This toy web server is used to illustrate security vulnerabilities. This web server only supports extremely simple HTTP GET requests.
****************************************************************/

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class SimpleWebServer {

    /* Run the HTTP server on this TCP port. */
    private static final int PORT = 8080;

    /* The socket used to process incoming connections from web clients */
    private static ServerSocket dServerSocket;

    public SimpleWebServer () throws Exception {
    	dServerSocket = new ServerSocket (PORT);
    }

    public void run() throws Exception {
    	System.out.println("Server is running...");
    	while (true) {
    		/* wait for a connection from a client */
    		Socket s = dServerSocket.accept();
    		/* then process the client's request */
    		processRequest(s);
    		//s.close();
    	}
    }

    /* Reads the HTTP request from the client, and responds with the file the user requested or a HTTP error code. */
    public void processRequest(Socket s) throws Exception {
    	/* used to read data from the client */
    	BufferedReader br = new BufferedReader (new InputStreamReader (s.getInputStream()));

    	/* used to write data to the client */
    	OutputStreamWriter osw =  new OutputStreamWriter (s.getOutputStream());

    	/* read the HTTP request from the client */
    	String request = br.readLine();

    	String command = null;
    	String sourcePathName = null;
    	String destPathName = null;
    	
    	/* parse the HTTP request */
    	StringTokenizer st = new StringTokenizer (request, " ");
    	try {
    		command = st.nextToken();
    		sourcePathName = st.nextToken();
    		if(st.hasMoreTokens()) {
    			destPathName = st.nextToken();
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Token process error!");
    		return;
    	}

    	if (command.equals("GET")) {
    		/* if the request is a GET try to respond with the file the user is requesting */
    		System.out.println("Path name: "+sourcePathName);
    		serveFile (osw,sourcePathName);
    	}
    	else if(command.equals("PUT")) {
    		storeFile(br, osw, destPathName);
    	}
    	else {
    		/* if the request is a NOT a GET or PUT, return an error saying this server does not implement the requested command */
    		osw.write ("HTTP/1.0 501 Not Implemented\n\n");
    	}

    	/* close the connection to the client */
    	osw.close();
    }

	/**
	 * serve file request according to the client request- no security check
	 * @param osw send stream to the client side
	 * @param pathname source file requested by the client
	 * @throws Exception
	 */
    public void serveFile (OutputStreamWriter osw, String pathname) throws Exception {
    	FileReader fr=null;
    	int c=-1;
    	int bytesCounter = 0; //max integer in java is: 2,147,483,647
    	int MAX_DOWNLOAD_LIMIT = 104857600; //MAX_DOWNLOAD_LIMIT = 100MB 104857600;
    	StringBuffer sb = new StringBuffer();

    	/* remove the initial slash at the beginning of the pathname in the request */
    	if (pathname.charAt(0)=='/')
    		pathname=pathname.substring(1);

    	/* if there was no filename specified by the client, serve the "index.html" file */
    	if (pathname.equals(""))
    		pathname="index.html";

    	/* try to open file specified by pathname */
    	try {
    		fr = new FileReader (pathname);
    		c = fr.read();
    	}
    	catch (Exception e) {
    		/* if the file is not found,return the appropriate HTTP response code  */
    		osw.write ("HTTP/1.0 404 Not Found\n\n");
    		return;
    	}

 	/* if the requested file can be successfully opened
 	   and read, then return an OK response code and
 	   send the contents of the file */
    	osw.write ("HTTP/1.0 200 OK\n\n");
    	while (c != -1 ) {
    		sb.append((char)c);
    		c = fr.read();
    		bytesCounter++;
    		if(bytesCounter > MAX_DOWNLOAD_LIMIT) {
    			sb.replace(0, sb.length(),"Requested file is too big to serve!"); //clean the Stringbuffer and send client error info
    			logEntry("error_log.txt", "403 Forbidden! Downloading file: " + pathname); //log the error info 1130
    			break;
    		}
    	}
    	osw.write (sb.toString());
    	logEntry("logfile.txt", "Read "+ pathname);
    	fr.close();
    	osw.flush();
    }

	/**
	 * Store file send by the client - no security check
	 * @param br read stream from client side
	 * @param osw send stream to client side
	 * @param pathname destination file
	 * @throws Exception
	 */
	public void storeFile(BufferedReader br, OutputStreamWriter osw, String pathname)throws Exception{
    	FileWriter fw = null;
    	try {
    		fw = new FileWriter(pathname);
    		String s = br.readLine();
    		while(s != null) {
    			fw.write(s+"\n");
    			s = br.readLine();
				if(s.equals("end")) {
					break;
				}
    		}
    		fw.close();
    		osw.write("HTTP/1.0 201 created\n\n");
    		
    		logEntry("logfile.txt", "write "+pathname);
    		
    		osw.flush();
    	}catch(Exception e) {
    		osw.write("HTTP/1.0 500 Internal Server Error!\n\n");
    		osw.flush();
    	}
    	System.out.println(pathname + " saved");
    }

	/**
	 * log the get/put info to the log file
	 * @param fileName log file name
	 * @param record log information
	 */
	public void logEntry(String fileName, String record) {
    	try {
	    	FileWriter fw = new FileWriter(fileName, true);
	    	fw.write((new Date()).toString() + " " + record +"\n");
	    	fw.close();
    	}
    	catch (IOException ex) {
    		System.out.println("Writing log file fails!");
    	}
    }

    /**
	 *  This method is called when the program is run from the command line.
	 *  */
    public static void main (String argv[]) throws Exception {
    	/* Create a SimpleWebServer object, and run it */
    	SimpleWebServer sws = new SimpleWebServer();
    	sws.run();
    }
}
