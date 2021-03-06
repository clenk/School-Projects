// Chris Lenk for COS 210
// Reverses the string given it on the command line

public class Reverse {
	
	// Reverses an array of characters
	// a[0+n] is the first character we want to swap
	// a[a.length-1-n] is the second character we want to swap
	public static char[] rev(char a[], int n) {		
		if (n == (Math.floor((a.length-1)/2))) { // Base Case - last swap
			if (n != a.length-1-n) { // If they were equal, we'd have an odd number of characters (ie. we found the middle so we're done swapping)
				// One last swap
				char temp = a[0+n];
				a[0+n] = a[a.length-1-n];
				a[a.length-1-n] = temp;
			}
			
			return a;
		}
		else { // Recursive step			
			// Swap 'em
			char temp = a[0+n];
			a[0+n] = a[a.length-1-n];
			a[a.length-1-n] = temp;
			
			return rev(a, n+1);
		}
	}

	public static void main(String[] args) {
		// Ensure correct number of command-line arguments
		if (args.length != 1) {
			System.out.println("Error: Must provide exactly one command-line argument, the string to be reversed!");
			return;
		}
		
		// Get the string to be reversed
		char arr[] = args[0].toCharArray();
		
		// Get the reversed string
		rev(arr, 0);
		
		// Print out the reversed string
		System.out.println(arr);
		
	}

}
