package ua.gajdamaka;

import java.util.Properties;
import java.io.*;

public class Config {
	
	private static final String PATH_CONFIG_FILE = "./ua/gajdamaka/config.xml";

	public static String IP_ADRESS;
	public static int PORT;

	static {
		Properties properties = new Properties();
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream(PATH_CONFIG_FILE);
			properties.loadFromXML(fis);

			IP_ADRESS = properties.getProperty("server.host");
			PORT = Integer.parseInt(properties.getProperty("server.port"));
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