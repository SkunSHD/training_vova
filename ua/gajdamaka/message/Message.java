package ua.gajdamaka.message;

import java.io.Serializable;

public class Message implements Serializable{
	
	private String msg;

	public Message(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}
}