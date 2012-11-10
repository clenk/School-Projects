//Chris Lenk for COS 326
// A solution to the Dining Philosophers problem using monitors.
// Note that the program will run infinitely and must be terminated manually.

import java.util.concurrent.locks.*;

//Philosopher Thread class
class Philosopher implements Runnable {
	int n; // The philosopher's number
	DiningPhilosophers dp;
	long createTime;
	
	// Constructor
	public Philosopher(int number, DiningPhilosophers dinPhi) {
		n = number;
		dp = dinPhi;
		createTime = System.currentTimeMillis();
	}
	
	// Just kill time to simulate eating
	private void eat() {
		long seconds = 0;
		while (seconds <= 5) {
			long ms = System.currentTimeMillis() - createTime;
			seconds = ms / 1000;
		}
	}
	
	@Override
	public void run() {
		while (true) {
			dp.takeForks(n);
			eat();
			dp.returnForks(n);
		}
	}

}

interface DiningServer {
	// Called by a philosopher when it wishes to eat
	public void takeForks(int philNumber);

	// Called by a philosopher when it is finished eating
	public void returnForks(int philNumber);
}

class DiningPhilosophers implements DiningServer {
	enum State {THINKING, HUNGRY, EATING};
	State[] state = new State[5];
	Condition[] self = new Condition[5];
	Lock lock = new ReentrantLock();
	
	// Constructor
	public DiningPhilosophers() {
		// Create the condition objects for the monitor
		for (int i = 0; i < 5; i++) {
			self[i] = lock.newCondition();
		}
		// Start off with all the philosophers thinking
		for (int i = 0; i < 5; i++) {
			state[i] = State.THINKING;
			System.out.println("Philosopher " + i + " is thinking");
		}
		// Start the threads
		for (int i = 0; i < 5; i++) {
			Philosopher phi = new Philosopher(i, this);
			Thread phlsr = new Thread(phi);
			phlsr.start();
		}
	}

	// When a philosopher gets hungry and tries to eat...
	@Override
	public void takeForks(int i) {
		lock.lock();
		
		state[i] = State.HUNGRY;
		System.out.println("Philosopher " + i + " is hungry");
		// See if we can eat now
		test(i);
		// If not, wait
		if (state[i] != State.EATING) {
			try {
				self[i].await();
			} catch (InterruptedException e) {
				System.out.println("Error: Interrupted Exception with philosopher " + i + " waiting for forks");
			}
		}
		
		lock.unlock();
	}

	// When a philosopher is done eating...
	@Override
	public void returnForks(int i) {
		lock.lock();
		
		state[i] = State.THINKING;
		System.out.println("Philosopher " + i + " is done eating");
		System.out.println("Philosopher " + i + " is thinking");
		// See if left and right neighbors need the forks
		test((i + 4) % 5);
		test((i + 1) % 5);
		
		lock.unlock();
	}
	
	// Tests whether the philosopher of the given index can eat, and sets it to eating if so
	private void test(int i) {
		if ( (state[(i + 4) % 5] != State.EATING) && 
				(state[i] == State.HUNGRY) &&
				(state[(i + 1) % 5] != State.EATING) )  {
			state[i] = State.EATING;
			System.out.println("Philosopher " + i + " is eating");
			self[i].signal(); // Let the other philosophers know something's changed
		}
	}

	// MAIN
	public static void main(String[] args) {
		DiningPhilosophers monitor = new DiningPhilosophers();
	}

}
