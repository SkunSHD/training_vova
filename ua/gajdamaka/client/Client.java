package ua.gajdamaka.client;

import java.net.*;
import java.io.*;

public class Client {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out; 

	public Client(Socket socket,  BufferedReader in, PrintWriter out) {
		this.socket = socket;
		this.in = in;
		this.out = out;
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