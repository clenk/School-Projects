package proj3;

// Models a process to be run on a simulated CPU
public class Process {
	private int id;
	private int period; // Constant interval at which it requires the CPU
	private int burstTime; // Time it takes to process this process
	private int timeLeft; // Time remaining before the process completes
	private boolean notified;
	private boolean runThisPeriod;
	
	// Constructor
	public Process(int period, int time, int id) {
		super();
		this.period = period;
		this.burstTime = time;
		this.timeLeft = burstTime;
		this.id = id;
		this.notified = false;
		this.runThisPeriod = false;
	}
	
	// Getters
	public int getPeriod() {
		return period;
	}
	public int getBurstTime() {
		return burstTime;
	}
	public int getID() {
		return id;
	}	
	public int getTimeLeft() {
		return timeLeft;
	}
	public boolean runThisPeriod() {
		return runThisPeriod;
	}
	
	// Setter
	public void setRunThisPeriod(boolean bool) {
		runThisPeriod = bool;
	}
	
	// Moves the process forward 1 clock tick
	public void advance() {
		timeLeft--;
	}
	
	// Resets the process (resets the time left to complete)
	public void resetTime() {
		timeLeft = burstTime;
		notified = false;
	}
	
	// Checks whether or not the process has run long enough to complete
	public boolean isComplete() {
		return (timeLeft == 0);
	}
	
	// Returns true if the scheduler has been notified that the process has completed
	public boolean getCompletionNotified() {
		return notified;
	}
	public void setCompletionNotified() {
		notified = true;
	}

	// Print all relevant information about this process
	public void print() {
		System.out.println("Process " + id);
		System.out.println("  Period: " + period);
		System.out.println("  Time: " + burstTime);
		System.out.println();
	}
	
	// Returns the least amount of time required to run all given processes' periods evenly 
	public static int periodLCM(Process[] procs) {
		int periods[] = new int[procs.length];
		for (int i = 0; i < procs.length; i++) {
			periods[i] = procs[i].getPeriod();
		}
		
		int result = periods[0];
		for (int i = 1; i < periods.length; i++) {
			result = result * (periods[i] / gcd(result, periods[i]));
		}
		return result;
	}
	
	// Returns greatest common denominator of two integers
	private static int gcd(int a, int b) {
		while (b > 0)
	    {
	        int temp = b;
	        b = a % b;
	        a = temp;
	    }
	    return a;
	}

	// Returns the longest period among the given processes
	public static int longestPeriod(Process[] procs) {
		int longest = 0;
		for (int i = 0; i < procs.length; i++) {
			if (procs[i].getPeriod() > longest) {
				longest = procs[i].getPeriod(); 
			}
		}
		return longest;
	}

	// Returns true if any the given processes are not completed
	public static boolean anyIncomplete(Process[] procs) {
		for (int i = 0; i < procs.length; i++) {
			if (!procs[i].getCompletionNotified()) {
				return true;
			}
		}
		return false;
	}
}
