package lab8;

// Christopher Lenk for COS 326 - Fall 2012
// Simulates a disk-scheduler using
// FCFS, SSTF, SCAN, C-SCAN, and LOOK algorithms
// Usage: java lab8 <number of cylinder requests> <cylinder to start at>

public class Lab8 {

	// Prints the reference string passed in
	public static void printRefStr(int[] ref) {
		System.out.print("Reference String: ");
		for (int i = 0; i < ref.length; i++) {
			System.out.print(ref[i] + " ");
		}
		System.out.print("\n");
	}
	
	// MAIN
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java lab8 <number of cylinder requests> <cylinder to start at>");
			return;
		}
		
		// Generate a random cylinder requests
		Generator ref = new Generator(Integer.parseInt(args[0]));
		int[] referenceString = ref.getCylinders();
		int start = Integer.parseInt(args[1]);
		printRefStr(referenceString);
		System.out.println("Starting head location: " + args[1]);
		
		// FCFS
		DiskScheduler fcfs = new FCFS(referenceString, start);
		int numFCFS = fcfs.serviceRequests();
		System.out.println("FCFS = " + numFCFS);
		
		// SSTF
		DiskScheduler sstf = new SSTF(referenceString, start);
		int numSSTF = sstf.serviceRequests();
		System.out.println("SSTF = " + numSSTF);
		
		// SCAN
		DiskScheduler SCAN = new SCAN(referenceString, start, ref.getLastCylinder());
		int numSCAN = SCAN.serviceRequests();
		System.out.println("SCAN = " + numSCAN);
		
		// CSCAN
		DiskScheduler CSCAN = new CSCAN(referenceString, start, ref.getLastCylinder());
		int numCSCAN = CSCAN.serviceRequests();
		System.out.println("C-SCAN = " + numCSCAN);
		
		// LOOK
		DiskScheduler LOOK= new LOOK(referenceString, start);
		int numLOOK = LOOK.serviceRequests();
		System.out.println("LOOK = " + numLOOK);
		
		
	}

}
