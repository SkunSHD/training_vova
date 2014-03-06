import java.util.*;

public class SortedString {
	
	private String[] array;
	private boolean flag_length;	
	private boolean flag_number;

	public SortedString(String[] args) {
		if (args.length != 0) {
			if (args[0].equals("-D")) {
				array = Arrays.copyOfRange(args ,1, args.length);
				flag_length = true;
			} else {
				array = args;
			}
		} else {
			System.out.println("You not input parameter. Try again");
		}
		print();
		sort();
		print();
	}

	private void print() {
		for (String s : array) {
			System.out.println(s);
		}
		System.out.println("------------------------");
	}

	public void sort() {
		if (flag_length){
				sortByLength();
		} else if(flag_number) {
				sortByNumber();
			} else {
				sortByABC();
			}
		 
	}

	private void sortByABC() {
		Arrays.sort(array);
	}

	private void sortByLength() {
		Arrays.sort(array, new Comparator<String>(){
  			public int compare(String s1,String s2) {
    			return s1.length() - s2.length();
    		}
		});
	}

	private void sortByNumber() {

	} 
		
}