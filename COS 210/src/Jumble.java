// Chris Lenk for COS 210

import java.io.*;
import java.util.*;

public class Jumble {
	private static String DICT_FILE = "dictionary.txt";
	
	// Returns the next permutation in lexicographical order
	// Throws an ArrayIndexOutOfBoundsException if the last permutation has already been reached
	public static int[] nextPerm(int[] a) throws ArrayIndexOutOfBoundsException {
		int n = a.length-1;
		
		// Find the pivot point
		int j = n - 1;
		while (a[j] > a[j+1]) {
			j = j - 1;
		}
		
		// Find the smallest number to the right that's smaller than the pivot
		int k = n;
		while (a[j] > a[k]) {
			k = k - 1;
		}
		
		// Swap
		int temp = a[j];
		a[j] = a[k];
		a[k] = temp;
		
		// Sort remaining
		int r = n;
		int s = j + 1;
		while (r > s) {
			// Swap r and s
			temp = a[r];
			a[r] = a[s];
			a[s] = temp;
			
			r--;
			s++;
		}
		
		return a;
	}

	// Reorders the characters in the given string according the permutation given as an array of integers
	public static String reorder(int[] a, String s) {
		char[] c = new char[a.length];
		
		for (int i = 0; i < a.length; i++) {
			c[i] = s.charAt(a[i]-1);
		}
		
		return String.valueOf(c);
	}
	
	// MAIN
	public static void main(String[] args) {
		// Make sure at least 1 word is provided
		if (args.length < 1) {
			System.out.println("Error: Please provide in the command line what word(s) to un-jumble!");
			return;
		}
		
		// Create the dictionary
		ArrayList<String> dict = new ArrayList<String>();
		BufferedReader br;
		try {
			File dictionary = new File(DICT_FILE);
			br = new BufferedReader(new FileReader(DICT_FILE));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Dictionary file not found!");
			return;
		}		
		try {
			while (br.ready()) {
				String line = br.readLine();
				dict.add(line);
			}
		} catch (IOException e) {
			System.err.println("Error: Line could not be read!");
		}
		
		// Run permutations for all the words on the command-line
		for (int i = 0; i < args.length; i++) {
			System.out.println("Possible English words for \'"+args[i]+"\':");
			
			// Check if first permutation (the string as given) is in the dictionary
			if (dict.contains(args[i])) {
				System.out.println(args[i]);
			}
			
			// Create array of numbers to represent the string
			int[] array = new int[args[i].length()];
			for (int j = 0; j < array.length; j++) {
				array[j] = j+1;
			}
			
			// List of words already found (to avoid duplicates)
			ArrayList<String> usedList = new ArrayList<String>();
			
			// Go through all permutations
			while (true) {
				try {
					// Get the next permutation
					int[] order = nextPerm(array);
					String word = reorder(order, args[i]);
					
					// Check if this permutation is in the dictionary and it hasn't been found already (duplicate)
					if (dict.contains(word) && !usedList.contains(word)) {
						System.out.println(word);
						usedList.add(word);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
			
			System.out.println();
		}
	}
}
