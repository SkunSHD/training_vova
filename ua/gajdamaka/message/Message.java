package ua.gajdamaka.message;

import java.io.Serializable;
import java.util.*;
import java.sql.Time;

public class Message implements Serializable{
	
	private String msg;
	private String login;
	private String ip_address;
	private String status;
	private Date date;
	private static final long serialVersionUID = 1L;

	public Message(String login, String msg, String ip_address, String status) {
		this.login = login; 
		this.msg = msg;
		this.ip_address = ip_address;
		this.status = status;
		date = Calendar.getInstance().getTime();
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getIpAddress() {
		return ip_address;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getDate() {
		Time time = new Time(date.getTime()); 
		return time.toString();
	}

}