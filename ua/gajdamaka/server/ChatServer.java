package ua.gajdamaka.server;

import java.net.*;
import java.io.*;


public class ChatServer {
	private static final int port = 1234;
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public ChatServer() {
		try {
			ss = new ServerSocket(port);
			printMsg("Waiting for a client ...");
			socket = ss.accept();
			printMsg("Got client..." + socket);
			in  = new BufferedReader(new 
     				InputStreamReader(socket.getInputStream()));
    		out = new PrintWriter(socket.getOutputStream(),true);
    	} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}		
	}

	public void listen () {
		String  msg;
   		printMsg("Wait for messages....");
   		try {
   			while ((msg = in.readLine()) != null) {
   				if (msg.equalsIgnoreCase("exit")) 
   					break;
   				printMsg(msg);
   			}
   		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		} finally {
			close();
		}
	}

	private void close() {
		try {
			out.close();
			in.close();
			socket.close();
			ss.close();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

	private void printMsg(String msg) {
		System.out.println(msg);
	} 

} 