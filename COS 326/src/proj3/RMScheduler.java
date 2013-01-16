package proj3;

import java.util.Arrays;

// Implements a Rate-Monotonic scheduling algorithm

public class RMScheduler implements Scheduler {
	int contextSwitches;
	int missedDeadlines;
	int cpuUtilization;
	PriorityProcess[] procs;
	int[] waitTimes;
	int totalTime;

	// CONSTRUCTOR
	// Schedules the processes passed in as an array
	public RMScheduler(Process[] p) {
		System.out.println("Rate-Monotonic Scheduling");
		System.out.println("======================================\n");
		
		// Assign priorities to each process
		// Shorter periods = higher priority; Longer periods = lower priority
		procs = new PriorityProcess[p.length];
		for (int i = 0; i < p.length; i++) {
			procs[i] = new PriorityProcess(p[i], Integer.MAX_VALUE-p[i].getPeriod(), p[i].getID());
		}
		waitTimes = new int[p.length];
		
		// Sort processes by priority
		Arrays.sort(procs, PriorityProcess.RMCOMPARATOR);
		
		// Start up the simulation; use LCM to ensure we get through a whole cycle 
		totalTime = Process.periodLCM(procs);
		int curProc = 0;
		System.out.println("Time 0: Process " + procs[0].getID() + " started");

		// Simulate time passing
		for (int i = 1; i <= totalTime; i++) {
			curProc = tickActions(curProc, i);
		}
		System.out.println();
		
		// Print out statistics
		System.out.printf("Worst CPU Utilization case: %.2f%%\n", getWorstCaseUtil());
		System.out.printf("Total CPU Utilization: %.2f%%\n", getCPUUtilization());
		System.out.println("Number of preemptions: " + getContextSwitches());
		if (getMissedDeadlines() == 0) {
			System.out.println("This sceduling of these processes is viable");
		} else {
			System.out.println("This sceduling of these processes is NOT viable!");
			System.out.println("Missed Deadlines: " + getMissedDeadlines());
		}
		System.out.printf("Performance (difference between worst case and actual utilization): %.2f%%\n", (getWorstCaseUtil() - getCPUUtilization()));
		System.out.println("Average wait time: " + getAvgWaitTime());
	}
	
	// Simulates time passing and scheduling
	public int tickActions(int currentProcess, int time) {
		// If there's a process to run, do so
		if (!procs[currentProcess].runThisPeriod() && !procs[currentProcess].isComplete()) {
			procs[currentProcess].advance();
		}	

		// Check if any processes have failed to complete on time
		for (int j = 0; j < procs.length; j++) {
			if (time != 0 && !procs[j].isComplete()
					&& time % procs[j].getPeriod() == 0) {
				System.out.println("Time " + time + ": Process " + procs[j].getID() + " unable to complete on time!");
				missedDeadlines++;
				return currentProcess;
			}
		}
		
		// If current process has completed its CPU burst
		if (procs[currentProcess].isComplete()) {
			if (!procs[currentProcess].getCompletionNotified()) {
				System.out.println("Time " + time + ": Process " + procs[currentProcess].getID() + " complete");
				procs[currentProcess].setCompletionNotified();
				procs[currentProcess].setRunThisPeriod(true);
			}
			
			// Start the process with the next highest priority if one exists
			if (currentProcess + 1 < procs.length && time != totalTime) {
				currentProcess++; // Array is already sorted
				if (!procs[currentProcess].isComplete()
						&& procs[currentProcess].getTimeLeft() != procs[currentProcess].getBurstTime()) {
					System.out.println("Time " + time + ": Process " + procs[currentProcess].getID() + " resumed");
				} else if (!procs[currentProcess].runThisPeriod()) {
					System.out.println("Time " + time + ": Process " + procs[currentProcess].getID() + " started");
					procs[currentProcess].resetTime();
					waitTimes[currentProcess] += (time % procs[currentProcess].getPeriod());
				}
			}
		}
		
		for (int j = 0; j < procs.length; j++) {
			// Check if any processes' periods have run out and it's time to run them again (if their priority is high enough for preemption)
			if (procs[currentProcess].getPriority() < procs[j].getPriority()
					&& !procs[currentProcess].runThisPeriod()
					&& time % procs[j].getPeriod() == 0
					&& time != totalTime) {
				if (!procs[currentProcess].isComplete()) {
					System.out.println("Time " + time + ": Process " + procs[currentProcess].getID() + " preempted");
					contextSwitches++;
				}
				System.out.println("Time " + time + ": Process " + procs[j].getID() + " started");
				currentProcess = j;
				procs[currentProcess].resetTime();
				waitTimes[currentProcess] += (time % procs[currentProcess].getPeriod());
			}

			// See if a process's period is up, and if so, reset their run-this-period status
			if (time % procs[j].getPeriod() == 0) {
				procs[j].setRunThisPeriod(false);
				
				// If all the processes are complete, start this one since it's the start of your period
				if (!Process.anyIncomplete(procs) && time != totalTime) {
					System.out.println("Time " + time + ": Process " + procs[j].getID() + " started");
					currentProcess = j;
					procs[currentProcess].resetTime();
					waitTimes[currentProcess] += (time % procs[currentProcess].getPeriod());
				}
			}
		}
		return currentProcess;
	}
	
	@Override
	public int getContextSwitches() {
		return contextSwitches;
	}

	@Override
	public int getMissedDeadlines() {
		return missedDeadlines;
	}

	@Override
	public double getCPUUtilization() {
		double total = 0;
		for (int i = 0; i < procs.length; i++) {
			double util = (double)procs[i].getBurstTime() / (double)procs[i].getPeriod();
			total += util;
		}
		return total * 100;
	}

	@Override
	public double getAvgWaitTime() {
		double total = 0;
		for (int i = 0; i < procs.length; i++) {
			total += waitTimes[i];
		}
		return total / procs.length;
	}

	// Returns the worst case possible for CPU utilization with the number of processes this scheduler has
	public double getWorstCaseUtil() {
		double n = procs.length;
		if (n == 1) {
			return 100;
		} else {
			return 2 * (Math.pow(2, (1 / n)) - 1) * 100;
		}
	}
}
