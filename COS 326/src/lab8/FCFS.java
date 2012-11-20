package lab8;

public class FCFS implements DiskScheduler {
	int[] refStr;
	int start;
	
	/* Constructor
	 * 
	 * @param refStr Integer array of cylinder service requests
	 * @param start  Cylinder to begin the algorithm at
	 */
	public FCFS(int[] refStr, int start) {
		this.refStr = refStr;
		this.start = start;
	}

	/* Services the requests using First-Come-First-Serve
	 * 
	 * @return The amount of head movement for this algorithm
	 */	
	@Override
	public int serviceRequests() {
		int head = start;
		int total = 0;
		
		// For each request...
		for (int i = 0; i < refStr.length; i++) {
			// Add to the total head movement
			total += Math.abs(head - refStr[i]);
			
			// Move the head
			head = refStr[i];
		}
		
		return total;
	}
}
