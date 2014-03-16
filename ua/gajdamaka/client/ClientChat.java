package ua.gajdamaka.client;	

import java.net.*;
import java.io.*;

public class ClientChat {

	private static final String IP_ADRESS = "localhost";
	private static final int PORT = 1234;



	public ClientChat() {
		try {
			Socket socket = new Socket(IP_ADRESS, PORT);
			System.out.println("Trying ..." + socket.getInetAddress().getHostAddress());
			System.out.println("Connected to localhost");
			BufferedReader in = new BufferedReader(new 
   				InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			String msg = null;
			while (true) {
				msg = in.readLine();
				if (msg.equalsIgnoreCase("exit")) {
					System.out.println("Connection closed by foreign host.");
					break;
				} else {
					out.println(msg);
					//out.flush();
				}
			}

		} catch(IOException ex) {
			ex.printStackTrace(System.err);
		}
	}  
	
}