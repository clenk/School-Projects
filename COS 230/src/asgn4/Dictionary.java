package asgn4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Christopher Lenk for COS 230
//Implements a dictionary that stores words and their
//Definitions and allows the dictionary to be queried

public class Dictionary {
	BSTree tree;
	
	//Constructor
	public Dictionary() {
		tree = new BSTree();
	}
	
	//Adds a word and its definition to the dictionary
	private void add(String word, String definition) {
		tree.insert(word, definition);
	}
	
	//Finds the given word and prints it out with its definition
	private void find(String word) {
		Node nd = tree.search(word);
		System.out.println(nd.getWord() + ": " + nd.getDef());
	}
	
	//Lists alphabetically all the words and definitions in the dictionary
	private void list() {
		tree.print();
	}
	
	//Lists all of the words in the dictionary and their definitions
	//which are greater or equal to the begin word and less than or equal to the end word
	private void list(String beginWord, String endWord) {
		tree.print(beginWord, endWord);
	}	
	
	//Loads and executes commands from the specified file
	private void load(String filename) {
		File f = new File(filename);
		try {
			Scanner sc = new Scanner(f);
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				perform(line);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Given file was not found!");
		}
	}
	
	//Displays the tree structure
	private void tree() {
		tree.preorder(tree.getRoot(), "");
	}
	
	//Deletes the word passed in
	private void delete(String word) {
		tree.delete(word);
	}
	
	//Performs the action specified by the line passed in
	private void perform(String line) {
		Scanner sc = new Scanner(line);
		
		while (sc.hasNext()) {
			String cmd = sc.next();
			
			if (cmd.equalsIgnoreCase("Add")) {
				if (sc.hasNext()) {
					String wrd = sc.next();
					
					if (sc.hasNextLine()) {
						String df = sc.nextLine().trim();
						add(wrd, df);
					} else {
						System.out.println("Definition not provided!");
					}
				} else {
					System.out.println("Word not provided!");
				}
			} else if (cmd.equalsIgnoreCase("Find")) {
				if (sc.hasNext()) {
					find(sc.next());
				} else {
					System.out.println("Word not provided!");
				}
			} else if (cmd.equalsIgnoreCase("List")) {
				if (sc.hasNext()) {
					String word1 = sc.next();
					String word2 = sc.next();
					list(word1, word2);
				} else {
					list();
				}
			} else if (cmd.equalsIgnoreCase("Load")) {
				load(sc.next());
			} else if (cmd.equalsIgnoreCase("Tree")) {
				tree();
			} else if (cmd.equalsIgnoreCase("Delete")) {
				if (sc.hasNext()) {
					delete(sc.next());
				} else {
					System.out.println("Word not provided!");
				}
			} else {
				System.out.println("Unknown command.");
			}
		}
	}

	//MAIN
	public static void main(String[] args) {
		//Create a new dictionary
		Dictionary dict = new Dictionary();
		
		//Go through all the command files specified in the arguments
		for (int i = 0; i < args.length; i++) {
			dict.load(args[i]); //load the commands of that file
		}
		
		//Read in commands in an interface like a command-line
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter Command: ");
		String command = kb.nextLine();
		while (!command.trim().equalsIgnoreCase("quit")) {
			dict.perform(command);
			System.out.print("Enter Command: ");
			command = kb.nextLine();
		}
	}

}
