package lab8;

import java.util.*;

public class SSTF implements DiskScheduler {
	ArrayList<Integer> refStr;
	int start;
	
	/* Constructor
	 * 
	 * @param refStr Integer array of cylinder service requests
	 * @param start  Cylinder to begin the algorithm at
	 */
	public SSTF(int[] refStr, int start) {
		this.refStr = new ArrayList<Integer>(refStr.length);
		for (int i = 0; i < refStr.length; i++) {
			this.refStr.add(refStr[i]);
		}
		this.start = start;
	}

	/* Services the requests using Shortest-Seek-Time-First
	 * 
	 * @return The amount of head movement for this algorithm
	 */	
	@Override
	public int serviceRequests() {
		int head = start;
		int total = 0;
		
		while (!refStr.isEmpty()) {
			// Find the location with the shortest seek time from current location
			int nearest = calcNext(head);
			
			// Add to the total head movement
			total += Math.abs(head - nearest);
			
			// Move the head
			head = nearest;
			refStr.remove(new Integer(nearest));
		}
		
		
		return total;
	}
	
	/* Caclulates which request in the reference string is the closest to a given location
	 * 
	 * @param loc The current head location
	 * @ return The next closest location
	 */
	public int calcNext(int loc) {
		Iterator<Integer> itr = refStr.iterator();
		int closest = refStr.get(0);
		while (itr.hasNext()) {
			int cur = itr.next();
			if (Math.abs(loc - cur) < Math.abs(loc - closest)) {
				closest = cur;
			}
		}
		return closest;
	}
}
