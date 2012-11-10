// Christopher Lenk for COS 326
// Project 1 - Java shell interface
// Uses code by Jan Goyvaerts from stackoverflow.com/questions/366202 and Figures 3.13 and 3.37 from the textbook

import java.io.*;
import java.util.regex.*;
import java.util.*;

public class jsh {

	// Returns an arraylist of command-line parameters (from Jan Goyvaerts)
	public static ArrayList<String> parseCmdLine(String line) {
		ArrayList<String> matchList = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(line);
		while (regexMatcher.find()) {
			if (regexMatcher.group(1) != null) {
				// Add double-quoted string without quotes
				matchList.add(regexMatcher.group(1));
			} else if (regexMatcher.group(2) != null) {
				// Add single-quoted string without quotes
				matchList.add(regexMatcher.group(2));
			} else {
				// Add unquoted word
				matchList.add(regexMatcher.group());
			}
		}
		return matchList;
	}

	// Passes along the output (from the process's input and error streams) to
	// the output of the java shell
	public static void pipe(Process p) {
		// Get output stream of process
		InputStream is_out = p.getInputStream();
		InputStreamReader isr_out = new InputStreamReader(is_out);
		BufferedReader br_out = new BufferedReader(isr_out);
		String outputLine_out = null;
		// Get error stream of process
		InputStream is_err = p.getErrorStream();
		InputStreamReader isr_err = new InputStreamReader(is_err);
		BufferedReader br_err = new BufferedReader(isr_err);
		String outputLine_err = null;
		try {
			// Print the output
			while ((outputLine_out = br_out.readLine()) != null
					|| (outputLine_err = br_err.readLine()) != null) {
				if (outputLine_out != null) {
					System.out.println(outputLine_out);
				}
				if (outputLine_err != null) {
					System.out.println(outputLine_err);
				}
			}
			// Housekeeping
			br_out.close();
			br_err.close();
		} catch (IOException e) {
			System.out.println("I/O Error!");
		}
	}

	public static void main(String[] args) {
		String commandLine = "";
		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));
		ArrayList<String> history = new ArrayList<String>(); // History of programs run
		File dir = new File(System.getProperty("user.dir")); // Working directory

		// Main program loop
		while (true) {
			// Read input
			System.out.print("jsh> ");
			try {
				commandLine = console.readLine().trim();
			} catch (IOException e) {
				System.out.println("I/O Error!");
			}

			// Handle enter key
			if (commandLine.equals("")) {
				continue;
				// Handle running the last command in the history
			} else if (commandLine.equals("!!")) {
				int idx = history.size() - 1;
				// Handle empty history
				if (idx < 0) {
					System.out
							.println("Error: Not enough entries in the history!");
					continue;
				}
				commandLine = history.get(idx);
				// Handle specific previous command
			} else if (commandLine.matches("![0-9]*")) {
				int i = Integer.parseInt(commandLine.substring(1));
				// Handle out-of-bounds errors
				if (i < 1 || i > history.size()) {
					System.out
							.println("Error: For '!<i>', i must be a valid number in the command history!");
					continue;
				} else {
					commandLine = history.get(i - 1);
				}
				// Handle exit
			} else if (commandLine.equalsIgnoreCase("exit")
					|| commandLine.equalsIgnoreCase("quit")
					|| commandLine.equalsIgnoreCase("stop")
					|| commandLine.equalsIgnoreCase("q")) {
				return;
			}
			// Handle 'history' command
			if (commandLine.equalsIgnoreCase("history")) {
				Iterator<String> history_itr = history.iterator();
				int count = 0;
				while (history_itr.hasNext()) {
					count++;
					System.out.println(count + " " + history_itr.next());
				}
				continue;
			}

			// Add the command to history (won't add '!!' or '!<i>' or 'history')
			history.add(commandLine);

			// Parse input and create the process
			ArrayList<String> command = parseCmdLine(commandLine);
			ProcessBuilder pb = new ProcessBuilder(command);
			
			// Handle changing directories
			if (command.get(0).equalsIgnoreCase("cd")) {
				// Check that there aren't too many parameters
				if (command.size() > 2) {
					System.out.println("Error: Too many parameters for 'cd' command!");
				// Change to user's home directory if no parameters given
				} else if (command.size() == 1) {
					dir = new File(System.getProperty("user.dir"));
				// Otherwise, change to the specified directory
				} else {
					File asis = new File(command.get(1));
					File append = new File(dir+ "/" + command.get(1));
					// Make sure the specified folder exists
					if (append.exists() && !command.get(1).equals("/")) {
						dir = append;
					} else if (asis.exists()) {
						dir = asis;
					} else if (dir.exists()) {
						System.out.println("Error: Not a valid directory!");
					}
				}
				continue;
			}

			// Start the specified process
			pb.directory(dir); // Change directory
			Process proc = null;
			try {
				proc = pb.start();
			} catch (IOException e) {
				System.out.println("Invalid command!");
				continue;
			}

			// Handle process's output
			pipe(proc);
		}
	}

}
