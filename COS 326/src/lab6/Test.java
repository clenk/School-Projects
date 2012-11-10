package lab6;

/**
 * Test harness for LRU and FIFO page replacement algorithms
 *
 * Usage:
 *	java [-Ddebug] Test <reference string size> <number of page frames>
 */

public class Test
{
	public static void main(String[] args) {
		PageGenerator ref = new PageGenerator(new Integer(args[0]).intValue());

		int[] referenceString = ref.getReferenceString();
		
		// Print out the reference string
		System.out.print("Reference string = ");
		for (int i = 0; i < referenceString.length; i++) {
			System.out.print(referenceString[i]+" ");
		}
		System.out.println("\n");

		/** Use either the FIFO or LRU algorithms */
		ReplacementAlgorithm fifo = new FIFO(new Integer(args[1]).intValue());
		
		// output a message when inserting a page
		for (int i = 0; i < referenceString.length; i++) {
			fifo.insert(referenceString[i]);
		}

		// report the total number of page faults
		System.out.println("FIFO faults = " + fifo.getPageFaultCount());
	}
}