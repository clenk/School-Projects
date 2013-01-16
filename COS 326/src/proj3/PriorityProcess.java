package proj3;

import java.util.Comparator;

public class PriorityProcess extends Process {
	int priority;
	
	// Comparator for sorting processes based on priority for Rate-Monotonic
	public static final Comparator<PriorityProcess> RMCOMPARATOR = new Comparator<PriorityProcess>() {
		@Override
		public int compare(PriorityProcess p1, PriorityProcess p2) {
			return p2.getPriority() - p1.getPriority();		
		}
	};
	
	// Comparator for sorting processes based on priority for Earliest-Deadline-First
	public static final Comparator<PriorityProcess> EDFCOMPARATOR = new Comparator<PriorityProcess>() {
		@Override
		public int compare(PriorityProcess p1, PriorityProcess p2) {
			return p1.getPriority() - p2.getPriority();		
		}
	};

	// Constructor
	public PriorityProcess(Process p, int priority, int id) {
		super(p.getPeriod(), p.getBurstTime(), id);
		this.priority = priority;
	}
	
	public PriorityProcess(int period, int deadline, int time, int priority, int id) {
		super(period, time, id);
		this.priority = priority;
	}

	// Getter
	public int getPriority() {
		return priority;
	}

	// Setter
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	// Print all relevant information about this process
	public void print() {
		super.print();
		System.out.println("  Priority: " + priority);
	}

	
}
