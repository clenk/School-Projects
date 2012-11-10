// Chris Lenk for COS 210

public class count {
	
	public static int countPairs(String s, int count) {
		if (s.length() < 3) {
			// Base case
			return count;
		} else	if ( s.charAt(0) == s.charAt(2)) {
			// Found a pair!
			count++;
			return countPairs(s.substring(1), count);
		}  else {
			// Recursion
			return countPairs(s.substring(1), count);
		}
	}

	public static void main(String[] args) {
		// Check for command line argument
		if (args.length != 1) {
			System.out.println("Please provide exactly one command line argument!");
			return;
		}
		
		// Get the string to be parsed
		String str = args[0];
		
		// Count the number of pairs in the given string
		int numPairs = countPairs(str, 0);
		
		// Print out the results
		System.out.print(str + " contains " + numPairs + " ");
		if (numPairs == 1) {
			System.out.print("pair");
		} else {
			System.out.print("pairs");
		}
	}

}
