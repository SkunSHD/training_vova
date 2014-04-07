package ua.gajdamaka.client;

import java.util.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ua.gajdamaka.message.Message;

public class UserList {
	
	private Map<String, Client> users = new  HashMap<String, Client>();
	private static Map<String, String> au = new  HashMap<String, String>();
	private static final String PATH_CONFIG_FILE = "./ua/gajdamaka/users.xml";

	static {
		try {
			File file = new File(PATH_CONFIG_FILE);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("user");
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		        	Element fstElmnt = (Element) fstNode;
					NodeList login_Lst = fstElmnt.getElementsByTagName("login");
					Element loginNmElmnt = (Element) login_Lst.item(0);
			    	NodeList loginNm = loginNmElmnt.getChildNodes();
			    	NodeList pass_Lst = fstElmnt.getElementsByTagName("pass");
			    	Element passNmElmnt = (Element) pass_Lst.item(0);
			    	NodeList passNm = passNmElmnt.getChildNodes();
			    	String login = ((Node) loginNm.item(0)).getNodeValue();
			    	String pass = ((Node) passNm.item(0)).getNodeValue();
			    	au.put(login, pass);
				}	
	  		}
	  		System.out.println(au);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public boolean authentication(String login, String pass) {
		for (Map.Entry<String, String> conf : au.entrySet()) {
			if ((conf.getKey().equals(login)) && (conf.getValue().equals(pass))) {
				return true;				
			}
		}
		return false;
	}
}