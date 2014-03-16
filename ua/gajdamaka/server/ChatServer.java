package ua.gajdamaka.server;

import java.net.*;
import java.io.*;


public class ChatServer {
	private static final int port = 1234;

	public ChatServer() {
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Waiting for a client ...");
			while (true) {
				Socket socket = null;
				while(socket == null) {
					socket = ss.accept();
					System.out.println("Joined a new client");
				}
			new Client(socket);
			}
		} catch (SocketException ex) {
            ex.printStackTrace(System.err);
    	} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}		
	}
} 