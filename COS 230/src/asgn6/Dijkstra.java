package asgn6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Christopher Lenk for COS 230
//Demonstrates Dijkstra's algorithm on a directional, weighted graph
//using cities and the distances between them as vertices and edges.

public class Dijkstra {
	private Graph graph;
	
	//Constructor
	public Dijkstra(String args[]) {
		int size = lineCount(args);
		graph = new Graph(size);
	}
	
	//Loads and executes commands from the specified file
	private void load(String filename) {
		File f = new File(filename);
		try {
			Scanner sc = new Scanner(f);
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				parseCmd(line);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Given file could not be found!");
		}
	}
	
	//Interprets and performs the command passed in
	private void parseCmd(String line) {
		Scanner sc = new Scanner(line);
		
		if (sc.hasNext()) {
			String cmd = sc.next();
			
			if (cmd.equalsIgnoreCase("Start")) {
				//Use the node specified on this line as the new starting point, and recalculate the shortest path
				String label = sc.next();
				graph.path(label);
			} else if (cmd.equalsIgnoreCase("End")) {
				//Use the node specified on this line as the new ending point, and print the shortest path to it 
				System.out.println();
				String end = sc.next();
				graph.setEndPoint(end);
				boolean bln = graph.pathTo(end);
				if (bln == false) {
					System.out.print("(No path to that endpoint could be found!)");
				}
				System.out.println();
			} else {
				//Add the nodes and the edge specified on this line to the graph if not already present
				String from = cmd;
				String to = sc.next();
				int dist = Integer.parseInt(sc.next());

				int start = graph.find(from);
				if (start < 0) {
					graph.addVertex(from);
					start = graph.find(from);
				}
				
				int end = graph.find(to);
				if (end < 0) {
					graph.addVertex(to);
					end = graph.find(to);
				}
				
				graph.addEdge(start, end, dist);
			}
		}
	}
	
	//Counts the number of lines in each of the files and totals them
	public int lineCount(String[] args) {
		int total = 0;
		
		for (int i = 0; i < args.length; i++) {
			File inFile = new File(args[i]);
			Scanner sc;
			try {
				sc = new Scanner(inFile);
			} catch (FileNotFoundException e) {
				System.out.println("Error: File not found!");
				return -1;
			}
			
			while (sc.hasNext()) {
				sc.nextLine();
				total++;
			}
		}
		return total;
	}
	
	//MAIN
	public static void main(String[] args) {
   		Dijkstra dk = new Dijkstra(args);
		
		//Go through all the command files specified in the arguments
		for (int i = 0; i < args.length; i++) {
			dk.load(args[i]); //load the commands of that file
		}		
	}
}
