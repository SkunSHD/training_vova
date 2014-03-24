package ua.gajdamaka.client;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import ua.gajdamaka.message.Message;
import ua.gajdamaka.server.ChatServer;

public class Client {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out; 

	public Client(Socket socket,  BufferedReader in, PrintWriter out) {
		this.socket = socket;
		this.in = in;
		this.out = out;
		if (ChatServer.getMessageHistory().deSer() != null) {
			ArrayList<Message> history = ChatServer.getMessageHistory().deSer().getHistoryMessage();
			for (Message msg : history) {
				this.out.println(msg.getMessage());
			}
		}
	}

	public BufferedReader getBufferedReader() {
		return in;
	}

	public PrintWriter getPrintWriter() {
		return out;
	}

	public Socket getSocket() {
		return socket;
	}
}