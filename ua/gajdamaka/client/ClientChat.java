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

	public ClientChat() {
			
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
			
		try {
			while (true) {
				System.out.print("Input your name: ");
				login = input.readLine();
				if (login != null) {
					out.writeUTF(login);
					out.flush();
					System.out.println("Trying ..." + socket.getInetAddress().getHostAddress());
					System.out.println("Connected to localhost");
					break;	
				}
			}
			while ((msg = input.readLine()) != null) {
				Message message = new Message(login, msg, socket.getRemoteSocketAddress().toString());
				if (msg.equalsIgnoreCase("exit")) {
					System.out.println("Connection closed by foreign host.");
					out.writeObject(message);
					break;
				} else {
					out.writeObject(message);
				}
			}
		} catch(IOException ex) {
			System.out.println("Error input stream");
		}
	}

	private void outputMessage() {
		Message mess;
    	try {
      		while ((mess = (Message) in.readObject()) != null) {
        		System.out.println("Login: " + mess.getLogin() + "   " + mess.getDate());
   				System.out.println("Message: " + mess.getMessage() );
        		if (mess.getMessage().equalsIgnoreCase("Bye")){
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
}