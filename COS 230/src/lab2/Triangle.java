package lab2;

//Christopher Lenk for COS 230
//Prints a triangle pattern of asterisks

public class Triangle {

	public static void printer(int num, int limit) {
		
		//Print a number of asterisks equal to what was passed in
		for (int i = 0; i < num; i++) {
			System.out.print("*");
		}
		System.out.print("\n"); //End the current line
		
		if (num < limit) {
			printer(num+1, limit);
		}

		//Print a number of asterisks equal to what was passed in
		for (int i = 0; i < num; i++) {
			System.out.print("*");
		}
		System.out.print("\n"); //End the current line
	}
	
	//Main
	public static void main(String[] args) {
		//Ensure correct number of command-line arguments
		if (args.length != 2) {
			System.err.println("Wrong number of arguments!");
			return;
		}
		
		int m;
		int n;
		
		//Store the command-line arguments as numbers
		try {
			m = Integer.parseInt(args[0]);
			n = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Arguments must be numbers.");
			return;
		}
		
		if (m > n) {
			System.err.println("First argument must be less than or equal to the second argument.");
			return;
		}
		
		printer(m, n);
	}
}
