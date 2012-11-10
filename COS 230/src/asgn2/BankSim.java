package asgn2;

import java.util.Random;

//Christopher Lenk for COS 230
//Simulates a bank, calculating the time spent by tellers waiting on customers

public class BankSim {
	
	//Runs through a simulation of a single day
	public static Times doTrial(int visits, int dayLength, int maxLength, int numTellers) {
		//Create a random number generator
		Random r = new Random();
		
		//Create a linked list
		LLPriorityQueue<Visit> list = new LLPriorityQueue<Visit>();
		
		//Populate the list with randomly generated visits
		for (int i = 0; i < visits; i++) {
			Visit v = new Visit(r, dayLength, maxLength, i+1);
			list.insert(v.time, v);
		}

		int timeWorst = 0; //Worst waiting time 
		int timeWaitTot = 0; //Total wait time for all customers
		int[] tellers = new int[numTellers]; //Holds times that each teller is busy until
		
		//Initialize the tellers' busy-till times
		for (int i = 0; i < tellers.length; i++) {
			tellers[i] = 0;
		}
		
		//Go through the day and calculate when each visit is serviced
		while (!list.isEmpty()) {
			int timeArrive;
			int timeTaken;
			int timeWait;
			
			//Get the next visit
			Visit v = list.remove();
			timeArrive = v.time;
			timeTaken = v.visitLength;
			
			//Get the busy-till time of the teller who will be available soonest
			int busyTill = tellers[0];
			int tellIndex = 0;
			for (int i = 0; i < tellers.length; i++) {
				if (busyTill > tellers[i]) {
					busyTill = tellers[i];
					tellIndex = i;
				}
			}
			
			//Calculate how long this customer had to wait
			timeWait = busyTill - timeArrive;
			if (timeWait < 0) {
				timeWait = 0;
			}
			
			//Calculate how long the teller waits around before the next customer arrives
			int tellerIdle = timeArrive - busyTill;
			if (tellerIdle > 0) {
				tellers[tellIndex] += tellerIdle;
			}
			tellers[tellIndex] += timeTaken;
			
			//Add this wait time to the running total
			timeWaitTot += timeWait;
			
			//See if this is the longest wait time, and if so record it
			if (timeWorst < timeWait) {
				timeWorst = timeWait;
			}
		}
		
		return new Times(timeWorst, timeWaitTot);
	}

	public static void main(String[] args) {
		//Make sure enough command-line arguments are provided 
		if(args.length < 5){
			System.err.println("Error: Please supply command-line arguments in the following order: <number of trials> <length of bank day> <number of visits> <max length of visit> <number of tellers>");
			return;
		}

		int trials; //Holds the number of trials
		int dayLength; //Holds the length of the bank day
		int visits; //Holds the number of visits per day
		int maxLength; //Holds the max length of visit
		int tellers; //Holds the number of tellers
		
		//Store the arguments in variables and throw an error if necessary
		try {
			trials = Integer.parseInt(args[0]);
			dayLength = Integer.parseInt(args[1]);
			visits = Integer.parseInt(args[2]);
			maxLength = Integer.parseInt(args[3]);
			tellers = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			System.err.println("Error: Command-line arguments must be numerals.");
			return;
		}

		int avgWait = 0; //For holding the average of the longest customer wait times
		int avgTotal = 0; //For holding the average of the total wait times
		
		//Run through the number of trials specified in the command-line
		for (int i = 0; i < trials; i++) {
			Times tm = doTrial(visits, dayLength, maxLength, tellers);
			
			avgWait += tm.getWorst();
			avgTotal += tm.getTotal();
			
			System.out.println("Trial "+(i+1)+": "+tm);
		}
		
		//Calculate the averages and print them out
		avgWait = avgWait / trials;
		avgTotal = avgTotal / trials;
		System.out.println("\nAverage longest wait time: "+avgWait);
		System.out.println("Average total wait time: "+avgTotal);
		
	}
}
