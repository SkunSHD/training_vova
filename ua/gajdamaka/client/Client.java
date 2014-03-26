package ua.gajdamaka.client;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.message.Message;
import ua.gajdamaka.server.ChatServer;

public class Client {
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out; 

	public Client(Socket socket,  ObjectInputStream in, ObjectOutputStream out) {
		this.socket = socket;
		this.in = in;
		this.out = out;
		if (ChatServer.getMessageHistory() != null) {
			ArrayList<Message> history = ChatServer.getMessageHistory().getHistoryMessage();
			try {
				for (Message message : history) {
					this.out.writeObject(message);
				}
			} catch (IOException ex) {
				System.out.println("IO error");
			}
		}
	}

	public ObjectInputStream getObjInStream() {
		return in;
	}

	public ObjectOutputStream getObjOutStream() {
		return out;
	}

	public Socket getSocket() {
		return socket;
	}
}