package lab10;

//Modified by Christopher Lenk for COS 230
//A dictionary program using Java's Collections

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dict {
	//Use string for the index, and an ArrayList of strings for the objects, in the map
	TreeMap<String, ArrayList<String>> map;
	
	public class QuitException extends Exception {
	}

	//Adds a word to the dictionary
	public void AddCmd(String word, String def) {
		word = word.toLowerCase(); //To ensure that words go in the correct order
		System.out.println("Add Cmd " + word);
		
		if (map.containsKey(word)) { //If word is already in the map
			ArrayList<String> entry = map.get(word);
			
			//Make sure it doesn't already have this definition
			if (!entry.contains(def)) { 
				entry.add(def); //Add the definition to the word
			}
		} else { //If word is NOT already in the map
			ArrayList<String> defList = new ArrayList<String>();
			defList.add(def);
			map.put(word, defList); //Add the word and its definition to the map
		}
	}

	//Prints out the given word followed by all definitions
	public void FindCmd(String word) {
		System.out.println("Find Cmd " + word);
		if (map.containsKey(word)) {
			ArrayList<String> defList = map.get(word);
			System.out.println(word);
			Iterator<String> itr = defList.iterator();
			while (itr.hasNext()) {
				System.out.println(" " + itr.next());
			}
		} else { //If word isn't in the dictionary
			System.out.println("Word not found.");
		}
	}

	//Lists all the words in the dictionary
	public void ListCmd() {
		System.out.println("List Cmd");
		Set<String> words = map.keySet();
		Iterator<String> itr = words.iterator();
		while (itr.hasNext()) {
			String word = itr.next();
			ArrayList<String> defList = map.get(word);
			System.out.println(word);
			Iterator<String> itr2 = defList.iterator();
			while (itr2.hasNext()) {
				System.out.println(" " + itr2.next());
			}
		}
	}

	//Lists the words in the dictionary between the two words passed in
	public void ListRangeCmd(String begin, String end) {
		System.out.println("ListRange Cmd ");
		Set<String> words = map.keySet();
		Iterator<String> itr = words.iterator();
		while (itr.hasNext()) {
			String cur = itr.next();
			if ((cur.compareToIgnoreCase(begin) >= 0) && (cur.compareToIgnoreCase(end) <= 0)) {
				ArrayList<String> defList = map.get(cur);
				System.out.println(cur);
				Iterator<String> itr2 = defList.iterator();
				while (itr2.hasNext()) {
					System.out.println(" " + itr2.next());
				}	
			}
		}
	}

	public void LoadCmd(String filename) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(filename));
		try {
			String line = read.readLine();
			while (line != null) {
				parseCommand(line);
				line = read.readLine();
			}
		} catch (QuitException e) {

		} finally {
			read.close();
		}
	}

	public void parseCommand(String line) throws QuitException, IOException {
		int pos = line.indexOf(' ');
		String cmd = "";
		if(pos == -1)
			cmd = line;
		else
			cmd = line.substring(0, pos);
	
		if ("Add".equalsIgnoreCase(cmd)) {
            String remline = line.substring(pos).trim();
            int wpos = remline.indexOf(' ');
            String word = remline.substring(0,wpos);
            remline = remline.substring(wpos);
            AddCmd(word, remline);
		} else if ("Find".equalsIgnoreCase(cmd)) {
			String word = line.substring(pos).trim();
			FindCmd(word);
		} else if ("List".equalsIgnoreCase(cmd)) {
			
			if(pos == -1) {
				ListCmd();
			} else {
				String remline = line.substring(pos).trim();
				int wpos = remline.indexOf(' ');
	            String begin = remline.substring(0,wpos).trim();
	            String end = remline.substring(wpos).trim();
	            ListRangeCmd(begin,end);
			}
		} else if ("Load".equalsIgnoreCase(cmd)) {
			String filename = line.substring(pos).trim();
			LoadCmd(filename);
		} else if ("Quit".equalsIgnoreCase(cmd)) {
			throw new QuitException();
		} else {
                  if(cmd.trim().length() > 0) {
			    System.err.println("Invalid Command " + cmd);
                  }
		}
	}

	public void run() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			System.out.print("> ");
			String line = read.readLine();
			while (line != null) {
				parseCommand(line);
				System.out.print("> ");
				line = read.readLine();
			}
		} catch (QuitException e) {
            System.out.println("Bye");
		} finally {
			read.close();
		}

	}

	//Constructor
	public Dict(String[] args) throws IOException {
		//Create the map
		map = new TreeMap<String, ArrayList<String>>();
		
		for (String fn : args) {
			LoadCmd(fn);
		}
	}

	public static void main(String[] args) throws IOException {
		Dict dict = new Dict(args);
		dict.run();
	}

}