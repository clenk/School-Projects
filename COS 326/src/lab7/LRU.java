package lab7;

// Christopher Lenk for COS 326

public class LRU extends ReplacementAlgorithm {
	private int[] table;
	private int nextPg; // The next page to be replaced, if a new insert requires it
	private int numFaults;
	private int[] clocks; // Sequence of pages
	private int clock; // Logical clock ticks 

	// Constructor
	public LRU(int pageFrameCount, int[] refStr) {
		super(pageFrameCount);
		table = new int[pageFrameCount];
		for (int i = 0; i < table.length; i++) {
			table[i] = -1;
		}
		nextPg = 0;
		numFaults = 0;
		clocks = new int[pageFrameCount];
		clock = 0;
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
		
		for (int i = 1; i < clocks.length; i++) {
			if (clocks[i] < clocks[curHighest]) {
				curHighest = i;
			}
		}
		
		nextPg = curHighest;
	}

	// Insert a new page using the OPT algorithm
	@Override
	public void insert(int pageNumber) {		
		clock++; // Update the clock
		
		// Check if there's a page fault
		boolean pf = !isIn(pageNumber);
		
		if (pf) {
			// Insert the page into the table and up the page fault count
			setNextPg();
			table[nextPg] = pageNumber;
			clocks[nextPg] = clock;
			numFaults++;
		} else { // If it's already in the page table, update the clock
			for (int i = 0; i < table.length; i++) {
				if (table[i] == pageNumber) {
					clocks[i] = clock;
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
