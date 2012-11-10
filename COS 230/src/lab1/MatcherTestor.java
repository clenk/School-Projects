package lab1;

/*
 * MatchTestor
 * Author: John M. Hunt
 * Test the brace / paren / bracket matching function for the
 * ADT Stack lab
 * read a line from a text file and feed it into the Matcher class 
 * under test
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MatcherTestor {

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Must supply file name");
			return;
		}
		Matcher m = new Matcher();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
	        String line = null;
	        while ((line=reader.readLine()) != null) {
	        	System.out.println(line);
	        	System.out.println("Match Result: "+m.matcher(line));
	        }
		} catch (FileNotFoundException e) {
			System.err.println("Could not read file "+args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}