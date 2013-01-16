package proj3;

import java.util.Random;

// This class generates processes that need to be run on a simulated CPU
public class Generator
{
	private static final int DEFAULT_MAX_COUNT = 4;
	
	int maxPeriod;
	int numProcs;
	Process[] procs;
	
	// Constructors
	public Generator() {
		this(new Random().nextInt(DEFAULT_MAX_COUNT)+2, 101);
	}
	
	public Generator(int count) {
		this(count, 101);
	}
	
	public Generator(int count, int max) {
		numProcs = count;
		maxPeriod = max+1;
		
		procs = new Process[numProcs];
		for (int i = 0; i < procs.length; i++) {
			procs[i] = genProcess(i+1);
		}
	}

	// Randomly generates a process with three values, t, d, and p,
	// such that 0 <= t <= d <= p.
	public Process genProcess(int number) {
		Random rand = new Random();
		
		// Add 10 to the randomly generated period to avoid values of 0 (and be divisible by 5)
		// Add 5 to the randomly generated time so that period and time aren't equal, and time < period
		// (Such a process would ensure that other processes cannot be scheduled to meet their deadlines)
		int period = rand.nextInt(maxPeriod)+10;
		int time = rand.nextInt(period)+5;
		
		// Ensure that the values are multiples of 5 (for simplicity)
		period = period - (period % 5);
		time = time - (time % 5);
		
		return new Process(period, time, number);
	}
	
	// Getter
	public Process[] getProcesses() {
		return procs;
	}
	
	// Prints out the generated processes
	public void print() {
		System.out.println("Number of processes: " + numProcs);
		System.out.println("Max. period: " + (maxPeriod-1) + "\n");
		
		for (int i = 0; i < procs.length; i++) {
			procs[i].print();
		}
	}
}
