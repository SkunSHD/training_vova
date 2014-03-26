package ua.gajdamaka.message;

import java.util.ArrayList;
import java.io.*;

public class MessageHistory implements Serializable{
	
	private ArrayList<Message> messages = new ArrayList<Message>(10);
	
	public void addMessage(Message msg) {
		if (messages.size() >= 10) {
			messages.remove(0);
		}
		messages.add(msg); 
	}

	public ArrayList<Message> getHistoryMessage() {
		return messages;
	}
}