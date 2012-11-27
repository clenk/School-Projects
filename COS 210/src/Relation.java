// Chris Lenk for COS 210 - Nov 2012
// Prompts user for pairs in a digraph,
// then outputs whether the relation is reflexive, symmetric,
// antisymmetric, or transitive.

import java.util.*;

public class Relation {
	int n; // The size of the set A = {1, 2, ..., n}
	
	// CONSTRUCTOR
	public Relation(int n) {
		this.n = n;
	}
	
	// Returns true if the given set of edges is reflexive
	public boolean isReflexive(HashMap<Integer, ArrayList<Integer>> pairs) {
		for (int i = 1; i <= n; i++) {
			if (!pairs.containsKey(i)) {
				return false;
			} else if (!pairs.get(i).contains(i)) {
				return false;
			}
		}
		return true;
	}
	
	// Returns true if the given set of edges is symmetric
	public boolean isSymmetric(HashMap<Integer, ArrayList<Integer>> pairs) {
		Boolean isIn;
		Iterator<Integer> keyItr = pairs.keySet().iterator();
		while (keyItr.hasNext()) { // Go through each key
			int a = keyItr.next();
			Iterator<Integer> valItr = pairs.get(a).iterator();
			while (valItr.hasNext()) { // Go through each value (each pair)
				int b = valItr.next();
				
				// Find the reverse pair in the set (if it exists in the set)
				isIn = false;
				Iterator<Integer> keyItr2 = pairs.keySet().iterator();
				while (keyItr2.hasNext()) { // Go through each key
					int x = keyItr2.next();
					Iterator<Integer> valItr2 = pairs.get(x).iterator();
					while (valItr2.hasNext()) { // Go through each value (each pair)
						int y = valItr2.next();
						
						// Make sure the reverse pair is in the set
						if (a == y && b == x) {
							isIn = true;
						}
					}	
				}
				
				if (!isIn) {
					return false;
				}
			}	
		}
		
		return true;
	}
	
	// Returns true if the given set of edges is antisymmetric
	public boolean isAntisymmetric(HashMap<Integer, ArrayList<Integer>> pairs) {
		Boolean satisfied;
		Iterator<Integer> keyItr = pairs.keySet().iterator();
		while (keyItr.hasNext()) { // Go through each key
			int a = keyItr.next();
			Iterator<Integer> valItr = pairs.get(a).iterator();
			while (valItr.hasNext()) { // Go through each value (each pair)
				int b = valItr.next();
				
				// Check if the reverse pair is in the set
				satisfied = false;
				Iterator<Integer> keyItr2 = pairs.keySet().iterator();
				while (keyItr2.hasNext()) { // Go through each key
					int x = keyItr2.next();
					Iterator<Integer> valItr2 = pairs.get(x).iterator();
					while (valItr2.hasNext()) { // Go through each value (each pair)
						int y = valItr2.next();
						
						// Make sure the reverse pair is in the set and that they are equal
						if (a == y && b == x && x == y) {
							satisfied = true;
						}
					}	
				}
				
				if (!satisfied) {
					return false;
				}
			}	
		}
		
		return true;
	}
	
	// Returns true if the given set of edges is transitive
	public boolean isTransitive(HashMap<Integer, ArrayList<Integer>> pairs) {
		Boolean satisfied;
		Iterator<Integer> keyItr = pairs.keySet().iterator();
		while (keyItr.hasNext()) { // Go through each key
			int a = keyItr.next();
			Iterator<Integer> valItr = pairs.get(a).iterator();
			while (valItr.hasNext()) { // Go through each value (each pair)
				int b = valItr.next();
				
				// Find all pairs with the previously found value as the key
				Iterator<Integer> keyItr2 = pairs.keySet().iterator();
				while (keyItr2.hasNext()) { // Go through each key
					int y = keyItr2.next();
					if (y == b) {
						Iterator<Integer> valItr2 = pairs.get(b).iterator();
						while (valItr2.hasNext()) { // Go through each value (each pair)
							int c = valItr2.next();
							
							// See if (a, c) is in the set
							satisfied = false;
							Iterator<Integer> keyItr3 = pairs.keySet().iterator();
							while (keyItr3.hasNext()) { // Go through each key
								int x = keyItr3.next();
								Iterator<Integer> valItr3 = pairs.get(x).iterator();
								while (valItr3.hasNext()) { // Go through each value (each pair)
									int z = valItr3.next();
									
									// Make sure (a,c) is in the set
									if (a == x && c == z) {
										satisfied = true;
									}
								}	
							}
							
							if (!satisfied) {
								return false;
							}
						}	
					}
				}
			}	
		}
		
		return true;
	}

	// MAIN
	public static void main(String[] args) {
		// Ensure proper command-line arguments
		if (args.length != 1) {
			System.out.println("Usage: java relation <number of elements in set>");
			return;
		}
		
		// Get n
		int n = Integer.parseInt(args[0]);
		Relation rel = new Relation(n);
		
		System.out.println("Enter pairs below separated by space. Enter 0 0 when done.");
		
		// Get coordinate pairs
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, ArrayList<Integer>> pairs = new HashMap<Integer, ArrayList<Integer>>();
		do {
			// Get user input
			System.out.print("> ");
			String line = sc.nextLine();
			String[] coords = line.split(" ");
			
			// Make sure exactly 2 values were given
			if (coords.length != 2) {
				System.out.println("Error: Exactly 2 values must be given!");
				continue;
			}

			// Get the pair as integers
			int a = Integer.parseInt(coords[0]);
			int b = Integer.parseInt(coords[1]);
			
			// Make sure vertices are in range
			if (a < 0 || b < 0 || a > n || b > n) {
				System.out.println("Error: Values must be between 0 and n (inclusive)!");
				continue;
			}
			
			// Stop getting pairs when "0 0" is entered
			if (a == 0 && b == 0) {
				break;
			}
			
			// Add the pair to the set
			if (pairs.containsKey(a)) { // If this 'a' IS already in the graph
				pairs.get(a).add(b);
			} else { // If this 'a' is NOT already in the graph
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(b);
				pairs.put(a, temp);
			}
			
		} while (true);
		
		// Check the properties of the graph
		System.out.print("\nReflexive = ");
		if (rel.isReflexive(pairs)) {
			System.out.print("yes");
		} else {
			System.out.print("no");
		}
		
		System.out.print("\nSymmetric = ");
		if (rel.isSymmetric(pairs)) {
			System.out.print("yes");
		} else {
			System.out.print("no");
		}
		
		System.out.print("\nAntisymmetric = ");
		if (rel.isAntisymmetric(pairs)) {
			System.out.print("yes");
		} else {
			System.out.print("no");
		}
		
		System.out.print("\nTransitive = ");
		if (rel.isTransitive(pairs)) {
			System.out.print("yes");
		} else {
			System.out.print("no");
		}
	}

}
