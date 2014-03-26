package ua.gajdamaka.client;	

import java.net.*;
import java.io.*;
import ua.gajdamaka.message.Message;


public class ClientChat{

	private static final String IP_ADRESS = "localhost";
	private static final int PORT = 1234;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String login;

	public ClientChat(String login) {
			
			try {
				socket = new Socket(IP_ADRESS, PORT);
				this.login = login;
				System.out.println("Trying ..." + socket.getInetAddress().getHostAddress());
				System.out.println("Connected to localhost");
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
   				System.out.println(mess.getMessage() );
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