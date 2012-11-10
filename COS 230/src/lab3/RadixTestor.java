package lab3;

/*
 * RadixTestor
 * Author: John M. Hunt
 * Test a Radix sort of a list of integers for the Radix Lab
 * This program creates a Queue of int, and loads it with values from
 * a text file, specified on the command line
 * It calls the sort method of the Radix class
 * It then removes and prints the ints from the queue 
 * returned by the sort method
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RadixTestor {

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Must supply file name");
			return;
		}
		Radix r = new Radix();
		Queue inq = new Queue();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
	        String line = null;
	        System.out.println("read file and echo input");
	        while ((line=reader.readLine()) != null) {
	        	System.out.println(line);
	        	inq.add(Integer.parseInt(line));
	        }
	        System.out.println("sort");
	        Queue outq = r.sort(inq);
	        System.out.println("display sorted numbers");
	        while(!outq.isEmpty()){
	        	int n = outq.remove();
	        	System.out.println(n);
	        }
		} catch (FileNotFoundException e) {
			System.err.println("Could not read file "+args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (QueueEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}