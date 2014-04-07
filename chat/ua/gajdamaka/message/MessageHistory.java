package ua.gajdamaka.message;

import java.util.ArrayList;
import java.io.*;
import ua.gajdamaka.Config;

public class MessageHistory implements Serializable{
	
	private ArrayList<Message> messages = new ArrayList<Message>(Config.MAX_MESSAGE);
	
	public void addMessage(Message msg) {
		if (messages.size() >= Config.MAX_MESSAGE) {
			messages.remove(0);
		}
		messages.add(msg); 
	}

	public ArrayList<Message> getHistoryMessage() {
		return messages;
	}
}