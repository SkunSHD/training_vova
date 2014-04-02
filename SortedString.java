import java.util.*;
//fixme ever use package system for all classes
//todo second condition not accepted
//todo third condition accepted particle(see comment to getting parameter)
//todo fourth condition incorrect you can use number of character in word
public class SortedString {

    //todo Arrays in java very unusable thing use some Collection instead of Array
    //fixme array it's not good name for anything
	private String[] array;
    //fixme boolean flag not good in that case, you can create comparator instead of flag and just use it in to sorting without any additional methods
	private boolean flag_length;
    //fixme unused variables bad style
	private boolean flag_number;
	private int index;

	public SortedString(String[] args) {
		if (args.length != 0) {
            //fixme read about system variable in Java(like -Dxmx=512M, -Djava.version)
			if (args[0].equals("-D")) {
				array = Arrays.copyOfRange(args ,1, args.length);
				flag_length = true;
			} else {
				array = args;
			}
			print("Default lines");
		} else {
            //fixme you done your program without any choice for user.
            // fixme Try to imagine: your OS after start just showed message "You not input parameter. Try again" and turn  off itself after that
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