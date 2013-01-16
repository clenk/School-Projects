package proj3;

// Models a real-time scheduling algorithm.
// The constructor schedules the processes and runs through the simulation.
// Use the getters to retrieve that statistics of the given algorithm.
public interface Scheduler
{
	// Returns the number of context switches this algorithm used
	public int getContextSwitches();
	
	// Returns the number deadlines processes missed using this algorithm
	public int getMissedDeadlines();
	
	// Returns the CPU utilization as a percentage
	public double getCPUUtilization();
	
	// Returns the average wait time of the processes using this algorithm 
	public double getAvgWaitTime();
}
