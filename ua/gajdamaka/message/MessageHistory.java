package ua.gajdamaka.message;

import java.util.ArrayList;
import java.io.*;

public class MessageHistory implements Serializable{
	
	private ArrayList<Message> messages = new ArrayList<Message>(10);
	private static final String PATH = "../history.dat"; 


	public void addMessage(Message msg) {
		if (messages.size() >= 10) {
			messages.remove(0);
		}
		messages.add(msg); 
		ser();
	}

	public ArrayList<Message> getHistoryMessage() {
		return messages;
	}

	private void ser() {
		try{
			FileOutputStream fos = new FileOutputStream(PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found! Out");
		} catch (IOException ex) {
			System.out.println("Record error! In");
		}
	}

	public MessageHistory deSer(){
		try{
			if (messages.size() != 0){
				FileInputStream fis = new FileInputStream(PATH);
				ObjectInputStream ois = new ObjectInputStream(fis);
				return (MessageHistory)ois.readObject();
			}
		} catch (ClassNotFoundException ex) { 
			System.out.println("Serializable error");
		} catch (FileNotFoundException ex) {
			System.out.println("File not found! In");
		} catch (IOException ex) {
			System.out.println("Record error! In");
		}
		return null;		
	}

}