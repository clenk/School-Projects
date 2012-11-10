package asgn1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

//Christopher Lenk for COS 230

public class NumberRange {

	public static void main(String[] args) {
		
		//Make a linked list to hold the data
		LinkedList list = new LinkedList();
		
		//Make a Scanner to read in the file
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(fis);
		
		//Read through the file and add nodes to the linked list
		while (sc.hasNext()) {
			//Get the next line
			String line = sc.nextLine();
			
			//Count number of numbers in the line
			StringTokenizer st = new StringTokenizer(line);
			int count = st.countTokens();
			
			//Make sure the line contains nothing but numbers and whitespace
			boolean num = true; //True if the line contains numbers and whitespace, false if anything else
			while (st.hasMoreTokens()) {
				try {
					Integer.parseInt(st.nextToken());
				} catch (NumberFormatException e) {
					num = false;
				}
			}
			
			//Check for Comments and  Errors
			if (line.trim().equals("")) {
				//Skip blank lines
			} else if (line.substring(0,1).equals("/")) {
				if (line.substring(0,2).equals("//")) {
					//Skip comment lines - embedded ifs so won't trip up on single-digit numbers
				}
			} else if (count > 2) {
				//Print error if more than 2 numbers on the line
				System.out.println("Error: Two many numbers on this line");
			} else if (!num) {
				//Print error if line contains chars other than numerals or whitespace
				System.out.println("Error: Line contains invalid characters");
			} else {
				//No-Errors case

				//Make a scanner for the line
				Scanner scline = new Scanner(line);
				//Variables to hold the beginning and end values for the current line
				long first = 0;
				long second = 0;
				
				//Get the first number 
				first = scline.nextLong();
				
				//Get the second number
				if (scline.hasNextLong()) {
					second = scline.nextLong();
				} else {
					second = first;
				}
				
				//Switch them if they're in the wrong order
				if (first > second) {
					long temp = 0;
					temp = first;
					first = second;
					second = temp;
				}
				
				//Add a node to the linked list with these values
				list.add(first, second);
			}
			
		}
		
		//Sort and collapse the nodes, then print the output
		list.sort();
		list.collapse();
		list.print();
		
	}
}
