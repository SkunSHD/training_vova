package ua.gajdamaka.client;	

import java.net.*;
import java.io.*;
import ua.gajdamaka.message.Message;
import ua.gajdamaka.Config;
import java.util.Scanner;


public class ClientChat{

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String login;
	private String pass;
	private String status;
	private boolean flag;

	public ClientChat() {
			login = "";
			status = "work";
			flag = true;
			try {
				socket = new Socket(Config.IP_ADRESS, Config.PORT);
				out = new ObjectOutputStream(socket.getOutputStream());
				in  = new ObjectInputStream(socket.getInputStream());
				if (socket != null) {
					Thread t_out = new Thread(new Runnable(){
						public void run() {
							outputMessage();
						}
					});
					Thread t_in = new Thread(new Runnable(){
						public void run() {
							inputMessage();
						}
					});
					t_out.start();
					t_in.start();
				}
			} catch(IOException ex) {
				System.out.println("Error Connected");
			}	
	}

	private void inputMessage() {
		String msg = null;
		BufferedReader input = new BufferedReader(new 
   				InputStreamReader(System.in));
		System.out.println("If you want to change the status, enter \"Change status\"");	
		try {
			while (true) {
				System.out.print("Input your login: ");
				login = input.readLine();
				if (login != null) {
					out.writeUTF(login);
					out.flush();
					break;	
				}
			}
			while (true) {
				System.out.print("Input your password: ");
				pass = input.readLine();
				if (pass != null) {
					out.writeUTF(pass);
					out.flush();
					break;	
				}
			}
			while ((msg = input.readLine()) != null) {
				if (flag){
					if (msg.equals("Change status")) {
						this.setStatus();
					} else {
						Message message = new Message(login, msg, 
							socket.getRemoteSocketAddress().toString(), status);
						if (msg.equalsIgnoreCase("exit")) {
							System.out.println("Connection closed by foreign host.");
							out.writeObject(message);
							break;
						} else {
							out.writeObject(message);
						}
					}
				} else { break; }
			}
		} catch(IOException ex) {
			System.out.println("Error input stream");
		}
	}

	private void outputMessage() {
		Message mess;
    	try {
      		while ((mess = (Message) in.readObject()) != null) {
        		System.out.println("Login: " + mess.getLogin() + " (Status: " 
        			+ mess.getStatus() + ")  " + mess.getDate());
   				System.out.println("Message: " + mess.getMessage() );
        		if (mess.getMessage().equals("Bye")) { 
        			in.close();
        			out.close();
          			socket.close();
          			break;
        		}
      			if(mess.getMessage().equals("Incorrectly entered data")){
       				System.out.println("Press the enter");
       				flag = false;
        			in.close();
        			out.close();
          			socket.close();
          			break;
        		}

      		} 
    	} catch (ClassNotFoundException ex) {
      		System.out.println("Class not found");
    	} catch (IOException ex) {
      		System.out.println("Error output stream");
    	}
	}

	private void setStatus() {
		int coice;
		Scanner scan = new Scanner(System.in); 
		do {
			System.out.println("Input your status");
			System.out.println("	1. Work");
			System.out.println("	2. I'm eating");
			System.out.println("	3. Sleep");
			System.out.print("Input number  : ");
			coice = Integer.parseInt(scan.next());
		}while(coice < 1 || coice > 3);		
		switch(coice) {
			case 1: 
				status = "work";
				break;
			case 2:
				status = "i'm eating";
				break;
			case 3:
				status = "sleep";
				break;
		}
		System.out.println("If you want to change the status, enter \"Change status\"");
	}
}