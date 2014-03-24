package ua.gajdamaka.client;	

import java.net.*;
import java.io.*;

public class ClientChat{

	private static final String IP_ADRESS = "localhost";
	private static final int PORT = 1234;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	private String login;

	public ClientChat(String login) {
			try {
				socket = new Socket(IP_ADRESS, PORT);
				this.login = login; 
				System.out.println("Trying ..." + socket.getInetAddress().getHostAddress());
				System.out.println("Connected to localhost");
			
				in  = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

				out = new PrintWriter(socket.getOutputStream(),true);
			} catch(IOException ex) {
				System.out.println("Error Connected");
			}
			if (socket != null) {
				Thread t_out = new Thread(new Runnable(){
					public void run() {
						outputMessage();
					}
				});
				Thread t_in = new Thread(new Runnable(){
					public void run() {
						inputMessage();
					}
				});
				t_out.start();
				t_in.start();
			}			
	}

	private void inputMessage() {
		String msg = null;
		BufferedReader input = new BufferedReader(new 
   				InputStreamReader(System.in));
		try {
			while ((msg = input.readLine()) != null) {
				if (msg.equalsIgnoreCase("exit")) {
					System.out.println("Connection closed by foreign host.");
					out.println(msg);
					break;
				} else {
					out.println(login + ": " + msg);
				}
			}
		} catch(IOException ex) {
			System.out.println("Error input stream");
		}
	}

	private void outputMessage() {
		String msg;
    	try {
      		while ((msg = in.readLine()) != null) {
        		System.out.println(msg);
        		if (msg.equalsIgnoreCase("Bye")){
        			in.close();
        			out.close();
          			socket.close();
          			break;
        		}
      		}
    	} catch (IOException ex) {
      		System.out.println("Error output stream");
    	}
	}
}