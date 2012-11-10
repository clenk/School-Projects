package lab0;

import java.util.*;

//Christopher Lenk for COS 230

public class ShuttleSort {
	private int[] arr; //holds the array to be sorted
	private int comps = 0; //counter for the number of comparisons
	private int swaps = 0; //counter for the number of swaps
	
	//Constructors
	public ShuttleSort(int numItems) {
		//Set-up
		arr = new int[numItems];
		Random gen = new Random();
		
		//Fill the array
		for (int n = 0; n < arr.length; n++) {
			arr[n] = gen.nextInt(900) + 100;
		}
	}
	public ShuttleSort() { //Just for testing
		arr = new int[]{7, 4, 1, 6, 3, 4, 2};
	}
	
	//Displays the contents of the array
	public void display() {
		for (int n = 0; n < arr.length; n++) {
			System.out.print(arr[n] + ", ");
		}
		System.out.print("\n");
	}
	
	//Sorts the contents of the array
	public void sort() {
		for (int m = 1; m < arr.length; m++) { //Go through each place in the array
			swap(m);
		}
	}
	
	//Swaps consecutive numbers in the array if needed
	public void swap(int x) {
		int temp = 0; //used for swapping
		
		comps++; //increment comparison counter
		
		if (arr[x-1] > arr[x]) { //Swap if needed
			temp = arr[x-1];
			arr[x-1] = arr[x];
			arr[x] = temp;
			
			swaps++; //increment swap counter
			
			if (x-1 > 0) { //Keep it in bounds for the array
				swap(x-1); //Check the next number to the left
			}
		}
	}
	
	//MAIN
	public static void main (String[] args) {
		int numItems = Integer.parseInt(args[0]);

		ShuttleSort ss = new ShuttleSort(numItems);
		
		//Print the array, then sort it, then print it again
		System.out.print("Before sort: ");
		ss.display();
		ss.sort();
		System.out.print("After sort: ");
		ss.display();

		//Print out the counters
		System.out.println("Number of comparisons: " + ss.comps);
		System.out.println("Number of swaps: " + ss.swaps);
	}
}
