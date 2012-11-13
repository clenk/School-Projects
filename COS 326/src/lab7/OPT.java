package lab7;

import java.util.ArrayList;

// Christopher Lenk for COS 326

public class OPT extends ReplacementAlgorithm {
	private int[] table;
	private int nextPg; // The next page to be replaced, if a new insert requires it
	private int numFaults;
	private ArrayList<Integer> seq; // Sequence of pages

	// Constructor
	public OPT(int pageFrameCount, int[] refStr) {
		super(pageFrameCount);
		table = new int[pageFrameCount];
		for (int i = 0; i < table.length; i++) {
			table[i] = -1;
		}
		nextPg = 0;
		numFaults = 0;
		
		seq = new ArrayList<Integer>();
		for (int i = 0; i < refStr.length; i++) {
			seq.add(refStr[i]);
		}
	}
	
	// Returns the number of page faults that have occured so far
	public int getPageFaultCount() {
		return numFaults;
	}
	
	// Returns true only if the given page number is currently in the page table
	public boolean isIn(int pageNumber) {
		boolean bool = false;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == pageNumber) bool = true;
		}
		return bool;
	}

	// Determines which page to replace by looking ahead to see which will not be used for the longest period of time
	private void setNextPg() {
		int curHighest = 0;
		
		// See if any page frames are empty (available)
		for (int i = 0; i < table.length; i++) {
			if (table[i] == -1) {
				curHighest = i;
				nextPg = curHighest;
				return;
			}
		}
		
		for (int i = 0; i < table.length; i++) {
			if (seq.indexOf(table[i]) == -1) { // If the current page in the page table isn't referenced again in the incoming sequence, just put the next one here, at the first available slot
				curHighest = i;
				break;
			}
			if (seq.indexOf(new Integer(table[i])) > curHighest) {
				System.out.println(i+" "+curHighest);
				curHighest = i;
			}
		}
		
		nextPg = curHighest;
	}

	// Insert a new page using the OPT algorithm
	@Override
	public void insert(int pageNumber) {
		// Take if off the sequence
		seq.remove(0);
		
		// Check if there's a page fault
		boolean pf = !isIn(pageNumber);
		
		if (pf) {
			// Insert the page into the table and up the page fault count
			setNextPg();
			table[nextPg] = pageNumber;
			numFaults++;
		}

		// Print out the contents of the page table
		for (int i = 0; i < pageFrameCount; i++) {
			System.out.print(i+1+" - ");
			if (table[i] == -1) {
				System.out.print("(empty)");
			} else {
				System.out.print(table[i]);
				if (pf && nextPg == i) {
					System.out.print(" (pf)");
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}
