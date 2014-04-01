package ua.gajdamaka;

import java.util.Properties;
import java.io.*;

public class Config {
	
	private static final String PATH_CONFIG_FILE = "./ua/gajdamaka/config.xml";

	public static int PORT;
	public static int MAX_MESSAGE;
	public static int MAX_USERS;

	static {
		Properties properties = new Properties();
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream(PATH_CONFIG_FILE);
			properties.loadFromXML(fis);

			PORT = Integer.parseInt(properties.getProperty("server.port"));
			MAX_MESSAGE = Integer.parseInt(properties.getProperty("server.max.message.count"));
			MAX_USERS = Integer.parseInt(properties.getProperty("server.max.users.count"));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found");
		} catch (IOException ex) {
			System.err.println("Error read/write");
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.err.println("Error read/write");
			} 
		}
	}

}