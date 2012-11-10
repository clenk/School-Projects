package asgn3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Christopher Lenk for COS 230
//
//Hyphens (-) and apostrophes (') are considered valid characters for a word in this program
//The only 1-letter words are "a," "A," and "I."

public class WordCounting {
	private static final int SIZE = 300007; //Size to make the hash table: nearest prime number to 300,000

	public static void main(String[] args) {	
		//Make sure correct number of command-line arguments are provided
		if (args.length != 1) {
			System.out.println("Please provide a single command-line argument containing the name of the file to be read in.");
			return;
		}

		//Create the hash table 
		HashTable<String> hash = new HashTable<String>(SIZE);
		
		//Needed for reading in the file
		BufferedReader in;
		String line;
		Scanner sc;
		//Regex for finding words, counting ' as a letter
		Pattern ptrn = Pattern.compile("[[\\w']&&[^\\d]]+");
		
		//Read in the file
		try {
			in = new BufferedReader(new FileReader(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		int wordcount = 0; //Keep track of the number of words in the document
		
		//Read in all the lines in the file and add them to the hash table
		try {
			while ( (line=in.readLine()) != null) {
				//Go through the line and read in each word separately
				sc = new Scanner(line);
				Matcher matcher = ptrn.matcher(line);
				while (matcher.find()) {
					String str = matcher.group();
					//Words differing only in their capitalization should be considered the same
					str = str.toLowerCase();
					//Keep out one-letter words except for 'a','A', and 'I'.
					if (str.length() > 1 || ((str.length() == 1) && ((str.equals("a")) || str.equals("i")) ) ) {
						hash.insert(str);
						wordcount++;
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		//Print the words along with their counts
		hash.print();

		//Print the total number of entries (distinct words)
		System.out.println("Total Entries: " + hash.totEntries());

		//Print the total number of words in the file
		System.out.println("Total Word Count: " + wordcount);

		//Print the total number of collisions
		System.out.println("Total Collisions: " + hash.totCols());
		
		//Print the average and maximum cluster size
		System.out.println("Average Cluster Size: " + hash.aveLength());
		System.out.println("Maximum Cluster Size: " + hash.maxLength());
	}
}
