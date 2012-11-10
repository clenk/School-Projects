package asgn5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Christopher Lenk for COS 230
//Determines how to pack a book order in the standard sized boxes provided by a shipping company.

/*----------------------------------------------
 * Reason for using a heap:
Greedy algorithms follow a heuristic of picking whichever choice brings the solution closer.
For this reason a data structure such as a priority queue, which arranges the choices in order, would work well.
A heap does the same thing, but with a better Big O (logN instead of N).
A heap does not allow for searching, but the greedy heuristic doesn't need to.
I implemented it using an array because it was easier and I could find the size to make the array
by counting the number of lines in the input file. 
  ----------------------------------------------*/

public class Packer {
	
	//Adds the given book to the given array of boxes
	public static void addTo(Book bk, Box[] bxs) {
		//Go through all boxes and add the book to the first one that has enough room
		int i;
		for (i = 0; bxs[i] != null && i < bxs.length; i++) {
			if (bk.getWeight() <= bxs[i].getSpaceLeft()) {
				bxs[i].add(bk);
				return;
			}
		}
		
		//If method gets this far, no box has room enough room so start on a new one
		if (bk.getWeight() <= 2*16) { //2 lb box
			bxs[i] = new Box(2);
			bxs[i].add(bk);
		} else if (bk.getWeight() <= 5*16) { //5 lb box
			bxs[i] = new Box(5);
			bxs[i].add(bk);
		} else if (bk.getWeight() <= 10*16) { //10 lb box
			bxs[i] = new Box(10);
			bxs[i].add(bk);
		} else {
			System.out.println("Error: \"" + bk.getTitle() + "\" is too heavy for any box!");
		}
	}
	
	//Prints out the contents of each box in the given array,
	//followed by the total weight and total unused space
	public static void print(Box[] bxs) {
		int totWeight = 0;
		int totUnused = 0;
		
		for (int i=0; bxs[i] != null && i < bxs.length; i++) {
			System.out.println("Box "+(i+1)+" is a "+bxs[i].getSize()+" pound box:");
			bxs[i].print();
			System.out.println();
			
			totWeight += bxs[i].getWeight();
			totUnused += bxs[i].getSpaceLeft();
		}

		System.out.println("Total Weight: "+totWeight);
		System.out.println("Total Unused: "+totUnused);
		System.out.println();
		
		countBoxes(bxs);
	}
	
	//Prints the number of each size of box in the given array, as well as the total
	public static void countBoxes(Box[] bxs) {
		int total = 0;
		int box2 = 0;
		int box5 = 0;
		int box10 = 0;
		
		for (int i=0; bxs[i] != null && i < bxs.length; i++) {
			total++;
			if (bxs[i].getSize() == 2) {
				box2++;
			} else if (bxs[i].getSize() == 5) {
				box5++;
			} else if (bxs[i].getSize() == 10) {
				box10++;
			}
		}

		System.out.println("2 Pound Boxes: " + box2);
		System.out.println("5 Pound Boxes: " + box5);
		System.out.println("10 Pound Boxes: " + box10);
		System.out.println("--------------------");
		System.out.println("Total Boxes: " + total);
	}
	
	//MAIN
	public static void main(String[] args) {
		//Check command-line arguments
		if (args.length != 1) {
			System.out.println("Error: Please provide exactly one order file!");
			return;
		}
		
		//Set up the input file scanner
		File inFile = new File(args[0]);
		Scanner sc1;
		Scanner sc2;
		try {
			sc1 = new Scanner(inFile);
			sc2 = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found!");
			return;
		}
		
		//Count the number of lines in the input file
		int lineCount = 0;
		while (sc2.hasNext()) {
			sc2.nextLine();
			lineCount++;
		}
		
		//Create the heap
		Heap hp = new Heap(lineCount);

		//Add each book in the input file to the heap
		while (sc1.hasNext()) {
			String line = sc1.nextLine();
			int firstSpc = line.indexOf(' ') +1;
			int lastSpc = line.lastIndexOf(' ') +1;

			try {
				int ref = Integer.parseInt(line.substring(0, firstSpc).trim());
				String title = line.substring(firstSpc, lastSpc).trim();
				int weight = Integer.parseInt(line.substring(lastSpc).trim());
				
				hp.insert(ref, title, weight);
			} catch (NumberFormatException e) {
				System.out.println("Error: Improperly formated file!");
			}
		}

		Box boxes[] = new Box[lineCount]; //Track all of the boxes in use
		
		//Go through the heap and pack each book
		while (!hp.isEmpty()) {
			Book bk = hp.remove();
			addTo(bk, boxes);
		}
		
		//Print out the packing order list
		System.out.println("Packing List\n");
		print(boxes);
	}
}