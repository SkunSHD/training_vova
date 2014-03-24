package ua.gajdamaka;

import ua.gajdamaka.client.*;
import java.util.Scanner;

public class Telnet {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Input your name: ");
		String login = scan.next();
		ClientChat client = new ClientChat(login);
	}
}