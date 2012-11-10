package lab7;

import java.util.ArrayList;
import java.util.Date;

// Christopher Lenk for COS 326

public class LRU extends ReplacementAlgorithm {
	private int[] table;
	private int nextPg; // The next page to be replaced, if a new insert requires it
	private int numFaults;
	private Date[] clocks; // Sequence of pages

	// Constructor
	public LRU(int pageFrameCount, int[] refStr) {
		super(pageFrameCount);
		table = new int[pageFrameCount];
		for (int i = 0; i < table.length; i++) {
			table[i] = -1;
		}
		nextPg = 0;
		numFaults = 0;
		clocks = new Date[pageFrameCount];
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

	// Determines which page to replace by seeing which was used the farthest in the past
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
		
		Date currentTime = new Date();
		for (int i = 0; i < table.length; i++) {
			if (clocks[i].before(currentTime)) {
				currentTime = clocks[i];
				curHighest = i;
			}
		}
		
		nextPg = curHighest;
	}

	// Insert a new page using the OPT algorithm
	@Override
	public void insert(int pageNumber) {
		// Check if there's a page fault
		boolean pf = !isIn(pageNumber);
		
		if (pf) {
			// Insert the page into the table and up the page fault count
			setNextPg();
			table[nextPg] = pageNumber;
			clocks[nextPg] = new Date();
			numFaults++;
		} else { // If it's already in the page table, update the 
			for (int i = 0; i < table.length; i++) {
				if (table[i] == pageNumber) {
					clocks[i] = new Date();
				}
			}
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
