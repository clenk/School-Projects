package proj2;

// Christopher Lenk for COS 326
// Implements a virtual memory manager to translate logical addresses to physical addresses
// Uses FIFO for updating the TLB 

import java.io.*;
import java.util.*;

public class AddressTranslator {
	public static String FILENAME = "BACKING_STORE.bin";
	public static int PG_TABLE_SIZE = 256;
	public static int OFFSET = 256; // Page and Frame size, in bytes
	public static int TLB_SIZE = 16;

	// Returns the entry in the TLB of the specified page, or -1 if not present
	public static int searchTLB(int[] tlb, int pageNum) {
		for (int i = 0; i < tlb.length; i++) {
			if (tlb[i] == pageNum) { // Found it!
				return i;
			}
		}
		return -1; // Not found
	}
	
	// MAIN
	public static void main(String[] args) {
		// Ensure an input file was given
		if (args.length != 1) {
			System.out.println("Usage: java AddressTranslator <input file>");
			return;
		}
		
		// Open the Backing Store file (hard disk)
		RandomAccessFile backingStore;
		try {
			File fileName = new File(FILENAME);
			backingStore = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not find backing store!");
			return;
		}
		
		// Create physical memory
		int[] physMem = new int[PG_TABLE_SIZE * OFFSET];
		int firstFreePhysAddr = 0;
		int firstFreePhysFrame = 0;
		
		// Create the page table
		int[] pageTable = new int[PG_TABLE_SIZE];
		for (int i = 0; i < pageTable.length; i++) {
			pageTable[i] = -1;
		}
		
		// Create the TLB
		int[] tlbPg = new int[TLB_SIZE]; // TLB page number entries
		int[] tlbFrm = new int[TLB_SIZE]; // TLB frame number entries
		for (int i = 0; i < TLB_SIZE; i++) {
			tlbPg[i] = -1;
			tlbFrm[i] = -1;
		}
		int nextTLB = 0; // Tracks which entry in the TLB will be replaced next (FIFO) 
		
		// For tracking performance
		double numPgFaults = 0;
		double numTLBHits = 0;
		double count = 0;
		
		// Open the input file
		try {
			Scanner sc = new Scanner(new File(args[0]));
			
			while (sc.hasNextInt()) { // For every logical address in the input file
				// Get the next logical address and extract from it the  page number and offset 
				int logicalAddr = sc.nextInt();
				int pageNum = (logicalAddr & 0xff00) >>> 8;
				int offset = logicalAddr & 0xff;
				int frame;
				
				// Check the TLB
				int tlbEntry = searchTLB(tlbPg, pageNum);
				if (tlbEntry != -1) { // It's in TLB
					frame = tlbFrm[tlbEntry];
					numTLBHits++;
				} else {
					// Check the page table
					if (pageTable[pageNum] == -1) { // Page fault!
						numPgFaults++;
						
						// Bring the page from the backing store into physical memory
						backingStore.seek(pageNum * OFFSET);
						for (int i = 0; i < OFFSET; i++) {
							int val = backingStore.readByte();
							physMem[firstFreePhysAddr] = val;
							firstFreePhysAddr++;
						}
						
						// Update the page table
						pageTable[pageNum] = firstFreePhysFrame;
						
						firstFreePhysFrame++;
					}
					
					// Get the frame number
					frame = pageTable[pageNum];
					
					// Add to the TLB
					tlbPg[nextTLB] = pageNum;
					tlbFrm[nextTLB] = frame;
					
					// Update for FIFO
					nextTLB++;
					if (nextTLB > TLB_SIZE-1) { // Keep it in bounds
						nextTLB = 0;
					}
				}
				
				// Get the requested value
				int physAddr = (frame*OFFSET)+offset;
				int value = physMem[physAddr];

				// Print the result
				System.out.println("Virtual address: "+logicalAddr+" Physical address: "+physAddr+" Value: "+value);
				
				count++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not find specified input file!");
			return;
		} catch (IOException e) {
			System.out.println("Error: Could not read from backing store!");
			return;
		}

		// Print performance statistics
		System.out.println("Page Fault Rate: " + (numPgFaults / count) * 100 + "%");
		System.out.println("TLB Hit Rate: " + (numTLBHits / count) * 100 + "%");
	}
}
