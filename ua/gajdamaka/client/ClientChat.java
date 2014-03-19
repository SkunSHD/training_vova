package ua.gajdamaka.client;	

import java.net.*;
import java.io.*;

public class ClientChat{

	private static final String IP_ADRESS = "localhost";
	private static final int PORT = 1234;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	public ClientChat() {
		try {
			socket = new Socket(IP_ADRESS, PORT);
			System.out.println("Trying ..." + socket.getInetAddress().getHostAddress());
			System.out.println("Connected to localhost");
			BufferedReader input = new BufferedReader(new 
   				InputStreamReader(System.in));

			in  = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(),true);
			String msg = null;

			Thread t = new Thread(new Runnable(){
				public void run() {
					String msg;
    				try {
      					while ((msg = in.readLine()) != null) {
        					System.out.println("Server: " + msg);
        					if (msg.equalsIgnoreCase("Bye")){
        						in.close();
        						out.close();
          						socket.close();
          						break;
        					}
      					}
    				} catch (IOException ex) {
      					ex.printStackTrace(System.err);
    				}
				}
			});
			t.start();
			while ((msg = input.readLine()) != null) {
				if (msg.equalsIgnoreCase("exit")) {
					System.out.println("Connection closed by foreign host.");
					out.println(msg);
					break;
				} else {
					out.println(msg);
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace(System.err);
		}
	}
}