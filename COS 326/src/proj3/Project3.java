package proj3;

import java.util.ArrayList;
import java.util.Scanner;

// Christopher Lenk for COS 326 - Fall 2012
// Simulates a real-time CPU scheduler using
// Rate-Monotonic and Earliest-Deadline-First algorithms.
// Usage: java project3 [number of processes]
// Or: java project3 <number of processes> [maximum period]
// Or, to be prompted for processes: java project3 prompt
// Or, to use the processes in the book's examples: java project3 debug
// Or, to display this help message: java project3 help
// If processes are randomly generated, their periods and times are in multiples of 5 for ease of use.

public class Project3 {
	
	// MAIN
	public static void main(String[] args) {
		// Generate some processes
		Generator gen;
		if (args.length < 1) {
			gen = new Generator();
			
			gen.print();

			// Run the algorithms
			RMScheduler rms = new RMScheduler(gen.getProcesses());
			System.out.println();
			EDFScheduler edf = new EDFScheduler(gen.getProcesses());
		} else if (args.length == 1) {
			if (args[0].equals("debug")) { // debug case - sample processes
				Process[] procArr = new Process[2];
				procArr[0] = new Process(50, 25, 1);
				procArr[1] = new Process(80, 35, 2);
				procArr[0].print();
				procArr[1].print();
				
				// Run the algorithms
				RMScheduler rms = new RMScheduler(procArr);
				System.out.println();
				EDFScheduler edf = new EDFScheduler(procArr);
			} else if (args[0].equals("help")) { // help case - display help message
				System.out.println("This program simulates a real-time CPU scheduler using Rate-Monotonic and Earliest-Deadline-First algorithms.");
				System.out.println("Usage: java project3 [number of processes]");
				System.out.println("Or: java project3 <number of processes> [maximum period]");
				System.out.println("Or, to be prompted for processes: java project3 prompt");
				System.out.println("Or, to use the processes in the book's examples: java project3 debug");
				System.out.println("Or, to display this help message: java project3 help");
				System.out.println("If processes are randomly generated, their periods and times are in multiples of 5 for ease of use.");
			} else if (args[0].equals("prompt")) { // prompt case - get process details from user
				Scanner sc = new Scanner(System.in);
				System.out.println("Type \"zzz\" when you're done.");
				System.out.println("Enter process details (period, then time to complete) separated by a space (e.g. \"25 5\"):");
				System.out.print("> ");
				String line = sc.nextLine();
				ArrayList<Process> procArr= new ArrayList<Process>();
				int count = 1;
				while (!line.equals("zzz")) {
					// Parse input and add a process
					String[] s = line.split(" ");
					int p = Integer.parseInt(s[0]);
					int t = Integer.parseInt(s[1]);
					procArr.add(new Process(p, t, count));
					
					// Ready for next input
					count++;
					System.out.print("> ");
					line = sc.nextLine();
				}
				
				if (procArr.size() == 0) return;

				Process[] prAr = new Process[procArr.size()]; 
				procArr.toArray( prAr);
				
				// Run the algorithms
				System.out.println();
				RMScheduler rms = new RMScheduler(prAr);
				System.out.println();
				EDFScheduler edf = new EDFScheduler(prAr);
			} else {
				int count = Integer.parseInt(args[0]);
				gen = new Generator(count);
				
				gen.print();

				// Run the algorithms
				RMScheduler rms = new RMScheduler(gen.getProcesses());
				System.out.println();
				EDFScheduler edf = new EDFScheduler(gen.getProcesses());
			}
		} else {
			int count = Integer.parseInt(args[0]);
			int max = Integer.parseInt(args[1]);
			gen = new Generator(count, max);
			
			gen.print();

			// Run the algorithms
			RMScheduler rms = new RMScheduler(gen.getProcesses());
			System.out.println();
			EDFScheduler edf = new EDFScheduler(gen.getProcesses());
		}
	}

}
