// Christopher Lenk for COS 210
// Simulates Burmese python population in the everglades
// Assumptions: Snakes never die, and females always produce two new snakes (one of each gender) each month from the second month on

import java.util.*;

public class Lab4 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n, m, pop;

		// Get n
		System.out.print("Give value of n (how many pairs of snakes to start with): ");
		try {
			n = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Error: Must provide an integer!");
			return;
		}

		// Get m
		System.out.print("Give value of m (how many months to simulate): ");
		try {
			m = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Error: Must provide an integer!");
			return;
		}

		// Initialize the population
		int[] popArr = new int[m];
		popArr[0] = 1; // First month
		popArr[1] = 2; //Second month
		
		// Simulate
		for (int month = 2; month < m; month++) {
			popArr[month] = popArr[month-1] +popArr[month-2];
		}

		System.out.println("\nPairs of snakes at start = " + n);
		System.out.println("Months = " + m);
		System.out.println("Population = " + popArr[m-1]);
	}

}
