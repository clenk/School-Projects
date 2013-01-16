// Chris Lenk for COS 210
// A single-player word-search game in square blocks of letters

import java.io.*;
import java.util.*;

public class Project2 {
	private static String DICT_FILE = "dictionary.txt";
	
	// MAIN
	public static void main(String[] args) {
		// Decide where the crossword puzzle comes from
		Scanner sc = new Scanner(System.in);
		boolean random;
		System.out.println("Select game mode: Press 1 to randomly generate a crossword puzzle, or press 2 to load one from a text file");
		System.out.print("> ");
		String mode = sc.nextLine();
		while (!mode.equals("1") && !mode.equals("2")) {
			System.out.println("Error: Not a valid game mode!");
			System.out.print("> ");
			mode = sc.nextLine();
		}
		if (mode.equals("1")) {
			random = true;
		} else {
			random = false;
		}
		System.out.println();
		
		// Create the dictionary
		ArrayList<String> dict = new ArrayList<String>();
		BufferedReader dictBR;
		try {
			dictBR = new BufferedReader(new FileReader(DICT_FILE));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Dictionary file not found!");
			return;
		}		
		try {
			while (dictBR.ready()) {
				String line = dictBR.readLine();
				dict.add(line);
			}
		} catch (IOException e) {
			System.err.println("Error: Dictionary could not be read!");
		}
		
		char[][] crossword;
		if (random) {
			// Randomly generate crossword between 5x5 and 10x10
			Random rand = new Random();
			int size = rand.nextInt(6) + 5;
			crossword = new char[size][size];
			
			// Fill it with random characters
			for (int i = 0; i < crossword.length; i++) {
				for (int j = 0; j < crossword[i].length; j++) {
					crossword[i][j] =(char)(rand.nextInt(26) + 'a'); 
				}
			}
		} else {
			// Read in crossword from file

			// Get input crossword puzzle file
			BufferedReader inBR;
			try {
				File input = new File(args[0]);
				inBR = new BufferedReader(new FileReader(input));
			} catch (FileNotFoundException e1) {
				System.out.println("Error: Input file not found!");
				return;
			}
			
			int size;
			try {
				size = Integer.parseInt(inBR.readLine());
			} catch (IOException e) {
				System.out.println("Error: Input file must start with the size of the puzzle!");
				try {
					inBR.close();
				} catch (IOException e1) {
				}
				return;
			}
			
			// Create the crossword
			crossword = new char[size][size];
			for (int i = 0; i < crossword.length; i++) {
				String line;
				try {
					line = inBR.readLine();
				} catch (IOException e) { // On error, close the input file and shut down the program
					System.out.println("Error: Can't read line " + (i+2) + " of input!");
					try {
						inBR.close();
					} catch (IOException e9) {
					}
					return;
				}
				// Get all the characters on a line
				for (int j = 0; j < crossword[i].length; j++) {
					try {
						crossword[i][j] = line.charAt(j);
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Error: Line " + (i+2) + " isn't long enough!");
						try {
							inBR.close();
						} catch (IOException e9) {
						}
						return;
					}
				}
			}
			
			// Close the file
			try {
				inBR.close();
			} catch (IOException e) {
			}
		}
		
		// Find all the words
		ArrayList<String> solution = findWords(crossword, dict);
		
		// Show the crossword to the player
		printCrossword(crossword);
		
		// Let user find words
		int score = 0;
		ArrayList<String> usrAnswer = new ArrayList<String>();
		System.out.println("\nType in words (at least 3 characters long!) as you find them, or type \"zzz\" when you think you're done.");
		System.out.print("> ");
		String word = sc.nextLine();
		while (!word.equals("zzz")) { // For each word typed in until "zzz" is entered
			// See if they've already gotten this word
			if (usrAnswer.contains(word)) {
				System.out.println("You already found this word!");
			// See if their word is actually in the solution
			} else if (solution.contains(word)) {
				System.out.println("Correct! +1");
				usrAnswer.add(word);
				score++;
			// See if the word is even in the dictionary
			} else if (dict.contains(word)) {
				System.out.println("Not in this crossword puzzle!");
			} else {
				System.out.println("Not a word!");
			}
			
			// Get the next word
			System.out.print("> ");
			word = sc.nextLine();
		}
		
		// Tell user their score
		System.out.println("Game over. Total Score: " + score + ".");
		
		// Tell the user which words they didn't find
		Iterator<String> solItr = solution.iterator();
		while (solItr.hasNext()) {
			String wd = solItr.next();
			if (usrAnswer.contains(wd)) {
				solItr.remove();
			}
		}
		if (solution.size() > 0) {
			System.out.println("Unfortunately, you missed:");
			Iterator<String> solItr2 = solution.iterator();
			while (solItr2.hasNext()) {
				String wrd = solItr2.next();
				System.out.println("    " + wrd);
				score--;
			}
			// Tell user the adjusted score
			System.out.println("Total adjusted score: " + score);
		}
	}
	
	// Prints out the given crossword array
	public static void printCrossword(char c[][]) {
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				System.out.print(c[i][j]);
			}
			System.out.println();
		}
	}
	
	// Finds all the words contained in the given crossword array that are in the given dictionary
	// Searches forwards, backwards, downwards, upwards, and diagonally
	public static ArrayList<String> findWords(char c[][], ArrayList<String> d) {
		ArrayList<String> list = new ArrayList<String>();
		
		// Find all horizontal words
		for (int i = 0; i < c.length; i++) { // for each row
			for (int j = 0; j < c[i].length; j++) { // for each letter in the row
				for (int k = 1; k < c[i].length-j+1; k++) { // for each word possible in the row
					// k is how long the word should be
					StringBuilder word = new StringBuilder();
					for (int m = 0; m < k; m++) { // for each letter in the word
						word.append(c[i][j+m]);
					}
					// A word must be at least 3 characters
					if (word.length() > 2) {
						list.add(word.toString());
						// Add the backwards version
						word.reverse();
						list.add(word.toString());
					}
				}
			}
		}
		
		// Find all vertical words
		for (int i = 0; i < c.length; i++) { // for each row
			for (int j = 0; j < c[i].length; j++) { // for each letter in the row
				for (int k = 1; k < c[j].length-i+1; k++) { // for each word possible in the row
					// k is how long the word should be
					StringBuilder word = new StringBuilder();
					for (int m = 0; m < k; m++) { // for each letter in the word
						word.append(c[i+m][j]);
					}
					// A word must be at least 3 characters
					if (word.length() > 2) {
						list.add(word.toString());
						// Add the backwards version
						word.reverse();
						list.add(word.toString());
					}
				}
			}
		}
		
		// Find all diagonal words below or in the main diagonal
		for (int i = c.length-1; i > -1; i--) { // for each row, going bottom-up
			for (int k = 1; k <= c[i].length-i; k++) { // for each word possible in the row
				// k is how long the word should be
				StringBuilder word = new StringBuilder();
				for (int m = 0; m < k; m++) { // for each letter in the word
					word.append(c[i+m][m]);
				}
				// A word must be at least 3 characters
				if (word.length() > 2) {
					list.add(word.toString());
					// Add the backwards version
					word.reverse();
					list.add(word.toString());
				}
			}
		}
		
		// Find all diagonal words above the main diagonal
		for (int i = 1; i < c.length; i++) { // for each col
			for (int k = 1; k <= (c.length - i); k++) { // for each word possible in the diagonal
				// k is how long the word should be
				StringBuilder word = new StringBuilder();
				for (int m = 0; m < k; m++) { // for each letter in the word
					word.append(c[m][i+m]);
				}
				// A word must be at least 3 characters
				if (word.length() > 2) {
					list.add(word.toString());
					// Add the backwards version
					word.reverse();
					list.add(word.toString());
				}
			}
		}
		
		// Remove words not in dictionary
		Iterator<String> itr = list.iterator();
		while (itr.hasNext()) {
			String wd = itr.next();
			if (!d.contains(wd)) {
				itr.remove();
			}
		}
		
		return list;
	}
}
