package lab7;

/**
 * Test harness for LRU and FIFO page replacement algorithms
 *
 * Usage:
 *	java [-Ddebug] Test <reference string size> <number of page frames>
 */

public class Test
{
	public static void main(String[] args) {
		// Check command-line input
		if (args.length != 2 ) {
			System.out.println("Must provide 2 numbers on the command-line: the reference string size, and the number of page frames!");
			return;
		}
		
		PageGenerator ref = new PageGenerator(new Integer(args[0]).intValue());

		int[] referenceString = ref.getReferenceString();
		
		// Print out the reference string
		System.out.print("Reference string = ");
		for (int i = 0; i < referenceString.length; i++) {
			System.out.print(referenceString[i]+" ");
		}
		System.out.println("\n");

		// FIFO Algorithm
		System.out.println("FIFO Algorithm\n");
		ReplacementAlgorithm fifo = new FIFO(new Integer(args[1]).intValue());
		for (int i = 0; i < referenceString.length; i++) {
			fifo.insert(referenceString[i]);
		}

		// report the total number of page faults
		System.out.println("FIFO faults = " + fifo.getPageFaultCount() + "\n");

		// OPT Algorithm
		System.out.println("OPT Algorithm\n");
		ReplacementAlgorithm opt = new OPT(new Integer(args[1]).intValue(), referenceString);
		for (int i = 0; i < referenceString.length; i++) {
			opt.insert(referenceString[i]);
		}

		// report the total number of page faults (OPT)
		System.out.println("OPT faults = " + opt.getPageFaultCount() + "\n");

		// LRU Algorithm
		System.out.println("LRU Algorithm\n");
		ReplacementAlgorithm lru = new LRU(new Integer(args[1]).intValue(), referenceString);
		for (int i = 0; i < referenceString.length; i++) {
			lru.insert(referenceString[i]);
		}

		// report the total number of page faults (LRU)
		System.out.println("LRU faults = " + lru.getPageFaultCount() + "\n");
	}
}