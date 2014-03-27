package ua.gajdamaka.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.client.UserList;
import ua.gajdamaka.message.MessageHistory;

public class ChatServer {
	private static final int PORT = 1234;
	private static UserList users = new UserList();
	private static MessageHistory messages = new MessageHistory();

	public ChatServer() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("Waiting for a client ...");
			while (true) {
					Socket socket = null;
						while(socket == null) {
							socket = ss.accept();
						}
					if (users.count() < 10){
						new ClientThread(socket);
					} else {
						socket.close();
						continue;	
					}
			}
		} catch (SocketException ex) {
            ex.printStackTrace(System.err);
    	} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}		
	}

	public synchronized static UserList getUserList() {
        return users;
    }

    public synchronized static MessageHistory getMessageHistory() {
        return messages;
    }
} 