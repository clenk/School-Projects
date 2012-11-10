package lab2;

//Christopher Lenk for COS 230

public class Teddy {
	
	//Implement the rules of the game
	public static boolean doit(int n) {
		
		if (n == 42 ) { //Goal reached
			return true;
		} else if ((n % 2) == 0 && doit(n-(n/2)) ) { 
				return true;
		} else if (((n % 3) == 0 || (n % 4) == 0) && doit( n - ((n%10)*((n%100)/10))) )  {
				return true;
		} else if (((n % 5) == 0) && doit(n-42) ) {
				return true;
		} else { //Goal unreachable
				return false;
		}
			
	}

	public static void main(String[] args) {
		//Make sure one command-line argument has been provided
		if (args.length != 1) {
			System.err.println("Please provide a single argument for the number of teddies to start with.");
			return;
		}
		
		int bears = Integer.parseInt(args[0]); //Holds the number of bears to start with
		boolean possible = doit(bears); //True if goal of 42 can be reached with that starting number
		
		//Print out if it is possible to win the game
		if (possible) {
			System.out.println("The game can be won with "+bears+" bears.");
		} else {
			System.out.println("The game cannot be won with "+bears+" bears.");
		}
		
	}
}
