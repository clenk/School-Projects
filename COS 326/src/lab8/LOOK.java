package lab8;

import java.util.*;

public class LOOK implements DiskScheduler {
	TreeMap<Integer, Integer> refStr;
	int start;
	
	/* Constructor
	 * 
	 * @param refStr Integer array of cylinder service requests
	 * @param start  Cylinder to begin the algorithm at
	 */
	public LOOK(int[] refStr, int start) {
		this.refStr = new TreeMap<Integer, Integer>();
		for (int i = 0; i < refStr.length; i++) {
			this.refStr.put(refStr[i], refStr[i]);
		}
		this.refStr.put(start, start);
		this.start = start;
	}

	/* Services the requests using C-Look, starting in the direction with fewer requests
	 * 
	 * @return The amount of head movement for this algorithm
	 */	
	@Override
	public int serviceRequests() {
		int head = start;
		int total = 0;

		// Split reference string into those less than the starting location and those greater
		SortedMap<Integer, Integer> lesser = refStr.headMap(start, false);
		SortedMap<Integer, Integer> greater = refStr.tailMap(start, false);

		int next;
		// Start going in the direction that has fewer references
		if (lesser.size() < greater.size()) {
			// Start with those less than the starting location...
			while (!lesser.isEmpty()) {
				// Get the next location, add the distance to the total, and remove that location
				next = lesser.lastKey();
				total += Math.abs(head - next);
				head = next;
				lesser.remove(new Integer(next));
			}
			// ...then do those greater than the starting location
			while (!greater.isEmpty()) {
				// Get the next location, add the distance to the total, and remove that location
				next = greater.firstKey();
				total += Math.abs(head - next);
				head = next;
				greater.remove(new Integer(next));
			}
		} else {
			// Start with those greater than the starting location...
			while (!greater.isEmpty()) {
				// Get the next location, add the distance to the total, and remove that location
				next = greater.firstKey();
				total += Math.abs(head - next);
				head = next;
				greater.remove(new Integer(next));
			}
			// ...then do those less than the starting location
			while (!lesser.isEmpty()) {
				// Get the next location, add the distance to the total, and remove that location
				next = lesser.lastKey();
				total += Math.abs(head - next);
				head = next;
				lesser.remove(new Integer(next));
			}
		}
		
		return total;
	}
}
