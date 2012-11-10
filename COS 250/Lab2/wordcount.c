/* Christopher Lenk for COS 250
   Counts the number of characters, words, and lines, and prints the counts. */

#include <stdio.h>
#include <stdlib.h>

int main( void ) {
	int chars = 0; // Character count
	int words = 0; // Word count
	int lines = 0; // Line count
	
	int c = getchar(); // Get the first character
	
	while (c > 0) { // Go through each character in the input
		chars++; // Increase character count
		
		if ( c == ' ' || c == '\t' || c == '\n' ) { // If character is whitespace
			while ( c == ' ' || c == '\t' || c == '\n' ) { // Keep going until the next character is NOT whitespace
			
				if (c == '\n') { // Increase line count
					lines++;
				}
		
				// Get the next character and increase the character count as needed
				c = getchar();
				if (c > 0) { 
					chars++;
				}
			}
			words++; // Increase the word count because the end of whitespace has been reached
		}
		
		c = getchar(); // Get the next character
	}
	
	// Print out the counts
	printf( "%d | %d | %d", chars, words, lines);
}