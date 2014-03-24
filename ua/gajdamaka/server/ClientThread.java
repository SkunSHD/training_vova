package ua.gajdamaka.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.client.Client;
import ua.gajdamaka.message.Message;

public class ClientThread extends Thread {
	
	private Socket socket;
	private Client client;

	public ClientThread(Socket socket) {
		this.socket = socket;
		this.start();
	}

	public void run() {
		String line;
		try {
			final BufferedReader in  = new BufferedReader(new 
   				InputStreamReader(socket.getInputStream()));
   			final PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
   			client = new Client(socket, in, out);
   			ChatServer.getUserList().addUser(client);   			
   			while (true) {
   				if ((line = in.readLine()) != null) {
   					Message msg = new Message(line);
   					if (msg.getMessage().equalsIgnoreCase("exit")) {
   						System.out.println("Client disconnected");
   						out.println("Bye");
   						in.close();
   						out.close();
   						socket.close(); 
   						break;
   					} else {
   						System.out.println(msg.getMessage());
   						ChatServer.getMessageHistory().addMessage(msg);
   						this.broadcast(ChatServer.getUserList().getList(), msg);
   					}
   				}	
   			}
   		} catch (IOException ex) {
			//ex.printStackTrace(System.err);
		}

	}

	public void broadcast(ArrayList<Client> list, Message msg) {
		for (Client cl : list) {
			if (cl != client) {
				cl.getPrintWriter().println(msg.getMessage());
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