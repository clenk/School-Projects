// Chris Lenk for COS 326

import java.io.*;
import java.util.*;

//Timer thread
class Timer implements Runnable {
	long seconds;
	long limit;
	long createTime;
	boolean keepRunning;
	boolean warning1;
	boolean warning2;

	// Constructor
	public Timer(int s) {
		seconds = 0;
		limit = s;
		createTime = System.currentTimeMillis();
		keepRunning = true;
		warning1 = false;
		warning2 = false;
	}

	// Returns the number of seconds left
	public long getSecs() {
		return limit - seconds;
	}

	public boolean isRunning() {
		return keepRunning;
	}

	// Stop the thread
	public void stopRunning() {
		keepRunning = false;
	}

	public void run() {
		// Run until we tell the thread not to or the time limit is up
		while (seconds < limit && keepRunning) {
			// Update the elapsed seconds count
			long ms = System.currentTimeMillis() - createTime;
			seconds = ms / 1000;
			
			// Warn the user
			long timeleft = limit - seconds; 
			if (timeleft == 5 && !warning1) {
				System.out.print("\nHurry! Only 5 more seconds!\n> ");
				warning1 = true;
			} else if (timeleft == 2 && !warning2) {
				System.out.print("\nHurry! Only 2 more seconds!\n> ");
				warning2 = true;
			}
		}
		stopRunning();
	}
}

public class lab3 {

	public static void main(String[] args) {
		// Check command line arguments and get the time limit
		if (args.length != 1) {
			System.out
					.println("Please provide exactly one command line argument (the time limit in seconds)!");
			return;
		}
		int secs = Integer.parseInt(args[0]);

		// For user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Pick a number from 1 to 100
		Random rand = new Random();
		int goal = rand.nextInt(100) + 1;
		System.err.println(goal);

		System.out.println("Hi-Lo Game");
		System.out
				.println("You have "
						+ secs
						+ " seconds to guess a number I am thinking of between 1 and 100.");

		// Start the timer
		Timer tmr = new Timer(secs);
		Thread timerThread = new Thread(tmr);
		timerThread.start();

		// Get user's guess
		System.out.print("> ");
		int guess;
		boolean solved = false;
		try {

			while (tmr.isRunning() && !solved) {
				while (!br.ready() && tmr.isRunning()) { // Wait till there is input
					Thread.sleep(1);
				}
				if (br.ready()) {
					System.out.print("> ");
					guess = Integer.parseInt(br.readLine());

					// Handle their guess
					if (guess == goal) {
						tmr.stopRunning();
						System.out.println("Congratulations! You guessed it!");
						solved = true;
					} else if (guess < goal) {
						System.out.print("Higher!\n> ");
					} else if (guess > goal) {
						System.out.print("Lower!\n> ");
					}
				}
			}

			if (!solved) { // Time's up and they didn't solve it
				System.out.println("Oops! Out of time! Try again.");
			}
		} catch (IOException e) {
			System.out.println("Error with input!");
			return;
		} catch (InterruptedException e) {
			System.out.println("Error: Thread interrupted!");
			return;
		}

	}

}
