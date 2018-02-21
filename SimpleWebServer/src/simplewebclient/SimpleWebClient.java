package simplewebclient;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class SimpleWebClient {
    private static final String hostName = "localhost";
    private static final int PORT = 8080;

	public static void main(String[] args) throws IOException, InterruptedException {
        try (
            Socket serverSocket = new Socket(hostName, PORT);
        	PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        )
        {
            String userInput = null;
            if ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                String command = null;
                String sourcePathName = null;
                StringTokenizer st = new StringTokenizer (userInput, " ");
                command = st.nextToken();
               
                if(command.equals("GET")) {
                	String response=in.readLine();
                	if (response!=null) {
                		System.out.println("Response from Server: ");
                		System.out.println(response);
                		while ((response=in.readLine())!=null) {
                			System.out.println(response);
                		}
                	}
                }
                else if (command.equals("PUT")) {
                	try {
                		sourcePathName = st.nextToken();
                		passFile(out, sourcePathName);    
                	/*	String response = null;
                		if ((response =in.readLine())!=null) {
                			System.out.println("Response from Server: ");
                			System.out.println(response);
                			while ((response=in.readLine())!=null) {
                				System.out.println(response);
                			}
                		}
                		*/
                	}
                	catch(Exception e ) {
                		System.out.println("Token process error!");
                		in.close();
                		stdIn.close();
                		serverSocket.close();
                	}
                }
                else {
                	System.out.println("Input error!");
                }
                out.close();
           }
            
           serverSocket.close();
        }
      
         catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +  hostName);
            System.exit(1);
        } 
    }
	
	@SuppressWarnings("resource")
	public static void passFile(PrintWriter out, String pathname) throws Exception{
		FileReader fr= null;
		int c = -1;
		StringBuffer sb = new StringBuffer();
		try {
			fr = new FileReader(pathname);
			c = fr.read();
		}
		catch (Exception e) {
			System.out.println("Input file not found!");
			return;
		}
		while(c!=-1) {
			sb.append((char)c);
			c = fr.read();
		}
		out.write(sb.toString());
		fr.close();
	}
}
