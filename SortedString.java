import java.util.Scanner;
import java.util.Arrays;

public class SortedString {
	
	private String[] array;	
	
	public SortedString(String[] args) {
		if (args.length != 0) {
			array = args;
		} else {
			array = keyBoard("Input text");	
		}
		print();
	}

	private void print() {
		for (String s : array) {
			System.out.println(s);
		}
		System.out.println("------------------------");
	}

	public void sortArray() {
		Arrays.sort(array);
	}

	private String[] keyBoard(String mess) {
		System.out.print(mess + ": ");
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		return line.split(" ");
	}

}