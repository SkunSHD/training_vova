import java.util.*;

public class SortedString {
	
	private String[] array;
	private boolean flag_length;	
	private boolean flag_number;
	private int index;

	public SortedString(String[] args) {
		if (args.length != 0) {
			if (args[0].equals("-D")) {
				array = Arrays.copyOfRange(args ,1, args.length);
				flag_length = true;
			} else {
				array = args;
			}
			print("Default lines");
		} else {
			System.out.println("You not input parameter. Try again");
		}

	}

	private void print(String mess) {
		System.out.println(mess + ":");
		for (String s : array) {
			System.out.print(s + " ");
		}
		System.out.println();
		System.out.println("##########################");
	}

	public void sort() {
		if (array != null) {
			if (flag_length){
					sortByLength();
			} else {
					sortByABC_Reg();
				}
			print("Sort lines"); 
		}
	}

	private void sortByABC() {
		Arrays.sort(array);
	}

	private void sortByABC_Reg() {
		Arrays.sort(array, new Comparator<String>(){
  			public int compare(String s1,String s2) {
  				return s1.compareToIgnoreCase(s2);
  			}
		});
	}

	private void sortByLength() {
		Arrays.sort(array, new Comparator<String>(){
  			public int compare(String s1,String s2) {	
    			return s1.length() - s2.length();
    		}
		});
	}
}