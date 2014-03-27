package ua.gajdamaka.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.client.UserList;
import ua.gajdamaka.message.MessageHistory;
import ua.gajdamaka.Config;

public class ChatServer {
	private static UserList users = new UserList();
	private static MessageHistory messages = new MessageHistory();

	public ChatServer() {
		try {
			ServerSocket ss = new ServerSocket(Config.PORT);
			System.out.println("Waiting for a client ...");
			while (true) {
					Socket socket = null;
						while(socket == null) {
							socket = ss.accept();
						}
					if (users.count() < Config.MAX_USERS){
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