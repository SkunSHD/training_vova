package ua.gajdamaka.client;

import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class UserList {
	
	private ArrayList<Client> list = new  ArrayList<Client>();

	public Client addUser(Socket socket, BufferedReader in, PrintWriter out) {
		Client client = new Client(socket, in, out); 
		list.add(client);
		return client;
	}

	public ArrayList getList() {
		return list;
	}

}