package ua.gajdamaka.client;

import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class UserList {
	
	private ArrayList<Client> list = new  ArrayList<Client>();

	public void addUser(Client client) {
		list.add(client);
	}

	public ArrayList<Client> getList() {
		return list;
	}

	public boolean findUser(Client client) {
		for (int i = 0; i < list.size(); i++) {
			if (client.equals(list.get(i))) {
				return true;
			} 
		}
		return false;
	}

}