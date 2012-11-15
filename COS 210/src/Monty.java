import java.util.Random;

// Christopher Lenk for COS 210 - Discrete Structures
// November 2012
// Simulates a game of the Monty Hall problem.
// Usage: monty <number of doors> <number of games to simulate>
// The number of doors must be between 3 and 1000, inclusive.

public class Monty {

	// MAIN
	public static void main(String[] args) {
		// Error-check: number of commandline arguments
		if (args.length != 2) {
			System.out.println("Usage: monty <number of doors> <number of games>");
			return;
		}
		
		// Set-up
		int numDoors = Integer.parseInt(args[0]);
		int numTrials = Integer.parseInt(args[1]);
		Random rand = new Random();
		int winner;
		int selection;
		int monty;
		int decide;
		boolean stay;
		double stayWin = 0;
		double stayTotal = 0;
		double switchWin = 0;
		double switchTotal = 0;
		
		// Game trial loop
		for (int i = 0; i < numTrials; i++) {
			// Pick winning door and set up doors
			winner = rand.nextInt(numDoors);
			
			// Have player pick a door
			selection = rand.nextInt(numDoors);
			
			// Have Monty open a door
			do {
				monty = rand.nextInt(numDoors);
			} while (monty == selection || monty == winner); 
			
			// Decide whether to stay or switch
			decide = rand.nextInt(2);
			if (decide == 0) stay = true;
			else stay = false;
			
			System.out.print("Trial "+(i+1)+": ");
			
			if (stay) {
				System.out.print("Stay - ");
				stayTotal++;
			}
			else {
				System.out.print("Switch - ");
				switchTotal++;
				int temp;
				do { // Pick another door
					temp = rand.nextInt(numDoors);
				} while (temp == selection || temp == monty);
				selection = temp;
			}
			
			// See if they won or not
			if (selection == winner) {
				System.out.println("won");
				
				if (stay) stayWin++;
				else switchWin++;
			}
			else {
				System.out.println("lost");
			}
		}
		
		// Determine experimental probabilities
		double probStay = 100 * (stayWin / stayTotal);
		double probSwitch = 100 * (switchWin / switchTotal);

		// Print out experimental probabilities
		System.out.println("\nStats for winning " + numTrials + " rounds:");
		System.out.printf("Stay = %2.2f%%\n", probStay);
		System.out.printf("Switch = %2.2f%%\n", probSwitch);

		// Determine theoretical probabilities
		double theoreticStay = 100 * (1 / (double)numDoors);
		double theoreticSwitch = 100 * (((numDoors-1) / (double)numDoors) * (1 / (double)(numDoors-2)));

		// Print out theoretical probabilities
		System.out.println("\nTheoretical probabilities for " + numDoors + " doors:");
		System.out.printf("Stay = %2.2f%%\n", theoreticStay);
		System.out.printf("Switch = %2.2f%%\n", theoreticSwitch);
	}
}