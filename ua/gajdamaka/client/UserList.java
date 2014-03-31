package ua.gajdamaka.client;

import java.util.*;
import java.net.*;
import java.io.*;
import ua.gajdamaka.message.Message;

public class UserList {
	
	private Map<String, Client> users = new  HashMap<String, Client>();

	public void addUser(String login, Client client) {
		if (!users.containsKey(login)){
			users.put(login, client);
		} else {
			int i = 1;
			while(users.containsKey(login)){
				login = login + i; 
				i++;
			}
			users.put(login, client);
		}
		System.out.println(login + " is connected to server!!! ");
		broadcast(login, client);
	}

	public void deleteUser(String login){
		users.remove(login);
	}

	public ArrayList<Client> getList() {
		ArrayList<Client> list = new ArrayList<Client>();
		for (Map.Entry<String, Client> user : users.entrySet()) {
			list.add(user.getValue());
		}
		return list;
	}

	public int count() {
		return users.size();
	}

	public void broadcast(String login, Client client) {
		Message message = new Message("Server", login + " is connected to server!!! ", "localhost", "Info message");
		try {
			for (Client cl : this.getList()) {
				if (cl != client) {
					cl.getObjOutStream().writeObject(message);
				}
			}
		} catch (IOException ex) {
			System.out.println("Connected error!!!");
		}
	}
}