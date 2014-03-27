package ua.gajdamaka.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.client.Client;
import ua.gajdamaka.message.Message;

public class ClientThread extends Thread {
	
	private Socket socket;
	private Client client;
	private Message message;

	public ClientThread(Socket socket) {
		this.socket = socket;
		this.start();
	}

	public void run() {
		String login;
		try {
			final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			final ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());
   			while(true) {
   				login = in.readUTF();
   				if (login != null) {
   					break;
   				}	
   			}   			
   			client = new Client(socket, in, out);
   			ChatServer.getUserList().addUser(login, client);   			
   			while (true) {
   				if ((message = (Message) in.readObject()) != null) {
   					if (message.getMessage().equalsIgnoreCase("exit")) {
   						System.out.println("Client disconnected");
   						Message msg = new Message("Server", "Bye", socket.getInetAddress().getHostAddress());
   						out.writeObject(msg);
   						ChatServer.getUserList().deleteUser(login);
   						in.close();
   						out.close();
   						socket.close(); 
   						break;
   					} else {
   						System.out.println("Login: " + message.getLogin() 
   							+ "  " + message.getDate());
   						System.out.println("Message :" +  message.getMessage());
   						ChatServer.getMessageHistory().addMessage(message);
   						this.broadcast(ChatServer.getUserList().getList(), message);
   					}
   				}	
   			}
   		} catch (ClassNotFoundException ex) {
   			System.out.println("Class not found");
   		} catch (IOException ex) {
			System.out.println("Run Connected error!!!");
		}

	}

	public void broadcast(ArrayList<Client> list, Message message) {
		try {
			for (Client cl : list) {
				if (cl != client) {
					cl.getObjOutStream().writeObject(message);
				}
			}
		} catch (IOException ex) {
			System.out.println("Connected error!!!");
		}
	}
}