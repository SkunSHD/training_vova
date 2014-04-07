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
		String login, pass;
		try {
			final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			final ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());
   			while(true) {
   				login = in.readUTF();
   				if (login != null) {
   					break;
   				}	
   			}
            while(true) {
               pass = in.readUTF();
               if (pass != null) {
                  break;
               }  
            }
            if (!ChatServer.getUserList().authentication(login, pass)) {
               Message msg1 = new Message("Server", "Incorrectly entered data", socket.getInetAddress().getHostAddress(), "Info mess");
               out.writeObject(msg1);
               in.close();
               out.close();
               socket.close();
            } else {
      			client = new Client(socket, in, out);
      			ChatServer.getUserList().addUser(login, client);   			
      		 	while (true) {
      	  			if ((message = (Message) in.readObject()) != null) {
   	     				if (message.getMessage().equalsIgnoreCase("exit")) {
   			   			System.out.println(login + " disconnected");
                        Message msg3 = new Message("Server", login + " disconnected", socket.getInetAddress().getHostAddress(), "Info mess");
                        this.broadcast(ChatServer.getUserList().getList(), msg3);
                        Message msg4 = new Message("Server", "Bye", socket.getInetAddress().getHostAddress(), "Info mess");
   						   out.writeObject(msg4);
   						   ChatServer.getUserList().deleteUser(login);
   						   in.close();
   						   out.close();
   						   socket.close(); 
   						   break;
                     } else {
   						   System.out.println("Login: " + message.getLogin() + " (Status: " 
                           + message.getStatus() + ")  " + message.getDate());
   						   System.out.println("Message :" +  message.getMessage());
   						   ChatServer.getMessageHistory().addMessage(message);
   						   this.broadcast(ChatServer.getUserList().getList(), message);
                     }
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