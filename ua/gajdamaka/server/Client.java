package ua.gajdamaka.server;

import java.net.*;
import java.io.*;

public class Client extends Thread {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Client(Socket socket) {
		this.socket = socket;
		this.start();
	}

	public void run() {
		String  msg;
   		try {
			in  = new BufferedReader(new 
   				InputStreamReader(socket.getInputStream()));
   			out = new PrintWriter(socket.getOutputStream(),true);
   			while ((msg = in.readLine()) != null) {
   				if (msg.equalsIgnoreCase("exit")) 
   					break;
   				System.out.println(msg);
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
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

}