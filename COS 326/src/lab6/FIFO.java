package lab6;

// Christopher Lenk for COS 326

public class FIFO extends ReplacementAlgorithm {
	private int[] table;
	private int nextPg; // The next page to be replaced, if a new insert requires it
	private int numFaults;

	// Constructor
	public FIFO(int pageFrameCount) {
		super(pageFrameCount);
		table = new int[pageFrameCount];
		for (int i = 0; i < table.length; i++) {
			table[i] = -1;
		}
		nextPg = 0;
		numFaults = 0;
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

	// Insert a new page using the FIFO algorithm
	@Override
	public void insert(int pageNumber) {
		// Check if there's a page fault
		boolean pf = !isIn(pageNumber);
		
		if (pf) {
			// Insert the page into the table and up the page fault count
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
		
		if (pf) {
			nextPg++; // Get ready for the next page-faulting insert
			if (nextPg > table.length-1) { // Keep it in bounds
				nextPg = 0;
			}
		}
	}
}
