package proj3;

import java.util.Arrays;

public class EDFScheduler implements Scheduler {
	int contextSwitches;
	int missedDeadlines;
	int cpuUtilization;
	PriorityProcess[] procs;
	int[] waitTimes;
	int totalTime;

	// CONSTRUCTOR
	// Schedules the processes passed in as an array
	public EDFScheduler(Process[] p) {
		System.out.println("Earliest-Deadline-First Scheduling");
		System.out.println("======================================\n");
		
		// Assign priorities to each process based on deadlines
		procs = new PriorityProcess[p.length];
		for (int i = 0; i < p.length; i++) {
			procs[i] = new PriorityProcess(p[i], p[i].getPeriod(), p[i].getID());
		}
		waitTimes = new int[p.length];
		
		// Sort processes by priority
		Arrays.sort(procs, PriorityProcess.EDFCOMPARATOR);
		
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
		boolean complete = false;
		if (procs[currentProcess].isComplete()) {
			if (!procs[currentProcess].getCompletionNotified()) {
				complete = true;
				System.out.println("Time " + time + ": Process " + procs[currentProcess].getID() + " complete");
				procs[currentProcess].setCompletionNotified();
				procs[currentProcess].setRunThisPeriod(true);
			}
		}
		
		boolean preempted = false;
		for (int j = 0; j < procs.length; j++) {
			// See if a process's period is up, and if so, update their priority and reset their run-this-period status
			boolean resorted = false;
			int j2 = -1;
			int cur2 = -1;
			if (time % procs[j].getPeriod() == 0) {
				procs[j].setRunThisPeriod(false);
				procs[j].setPriority(procs[j].getPriority() + procs[j].getPeriod());
				// Re-sort the processes but keep track of the current one
				int curId = procs[currentProcess].getID();
				j2 = procs[j].getID();
				Arrays.sort(procs, PriorityProcess.EDFCOMPARATOR);
				// Save current values before we change them
				for (int k = 0; k < procs.length; k++) {
					if (procs[k].getID() == curId) {
						cur2 = currentProcess;
						currentProcess = k;
					}
					if (procs[k].getID() == j2) {
						j2 = j;
						j = k;
					}
				}
				resorted = true;
				
				// If all the processes are complete, start this one since it's the start of your period
				if (!Process.anyIncomplete(procs) && time != totalTime) {
					System.out.println("Time " + time + ": Process " + procs[j].getID() + " started");
					currentProcess = j;
					procs[currentProcess].resetTime();
					waitTimes[currentProcess] += (time % procs[currentProcess].getPeriod());
				}
			}
			
			// Check if any processes' periods have run out and it's time to run them again (if their priority is high enough for preemption)
			if (procs[j].getPriority() < procs[currentProcess].getPriority()
					&& time % procs[j].getPeriod() == 0
					&& time != totalTime) {
				if (!procs[currentProcess].isComplete()) {
					preempted = true;
					System.out.println("Time " + time + ": Process " + procs[currentProcess].getID() + " preempted");
					contextSwitches++;
				}
				System.out.println("Time " + time + ": Process " + procs[j].getID() + " started");
				currentProcess = j;
				procs[currentProcess].resetTime();
				waitTimes[currentProcess] += (time % procs[currentProcess].getPeriod());
			}
			
			// If priorities were changed, no need to do so again
			if (resorted) {
				resorted = false;
				break;
			}
			
			if (j2 != -1) {
				j = j2;
			}
		}
		
		if (complete && !preempted) {		
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
