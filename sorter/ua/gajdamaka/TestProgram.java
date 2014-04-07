import java.util.Scanner;

public class TestProgram {
	
	public static void main(String[] args) {
		SortedString ss;
		String[] line = args;
		Scanner scan = new Scanner(System.in);
		while(true) {
			ss = new SortedString(args); 
			ss.sort();
			System.out.print("You want to close an application? (Y/N): ");
			if (scan.nextLine().equals("Y")) {
				System.out.println("By");
				break;
			} else {
				System.out.print("Input line: ");
				line = scan.nextLine().split(" ");
			}
		}
	}  
}