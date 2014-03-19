package ua.gajdamaka.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.client.Client;

public class ClientThread extends Thread {
	
	private Socket socket;
	private Client client;

	public ClientThread(Socket socket) {
		this.socket = socket;
		this.start();
	}

	public void run() {
		String  msg;
   		try {
			final BufferedReader in  = new BufferedReader(new 
   				InputStreamReader(socket.getInputStream()));
   			final PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
   			client = ChatServer.getUserList().addUser(socket, in, out);   			
   			while (true) {
   				if ((msg = in.readLine()) != null) {
   					if (msg.equalsIgnoreCase("exit")) {
   						System.out.println("Client disconnected");
   						out.println("Bye");
   						in.close();
   						out.close();
   						socket.close(); 
   						break;
   					} else {
   						System.out.println(msg);
   						this.broadcast(ChatServer.getUserList().getList(), msg);
   					}
   				}	
   			}
   		} catch (IOException ex) {
			//ex.printStackTrace(System.err);
		}

	}

	public void broadcast(ArrayList<Client> list, String msg) {
		for (Client cl : list) {
			if (cl != client) {
				cl.getPrintWriter().println(msg);
			}
		}
	}

	private void close() {
		try {
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

}