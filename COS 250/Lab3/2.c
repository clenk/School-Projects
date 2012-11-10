/* Christopher Lenk for COS 250
   Reads in standard input lines and prints out only any identical,
   adjacent lines (but only once). */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define STRLEN 128 // Max. length of lines

int main(void) {
	char inLine[STRLEN]; // To hold the current line being read in
	char old[STRLEN] = ""; // To hold the last line
	int identical = 0; // Boolean: 1 (true) if the last pair of lines were identical
	
	while (fgets(inLine, STRLEN, stdin) != NULL) { // Get the input line
		if (strncmp(inLine, old, STRLEN) == 0) { // Check if this line is identical to the last one
			identical = 1;
		} else if (identical) { // If it's not but the last one was, print the last one
			printf("%s", old);
			identical = 0;
		}
		
		strncpy(old, inLine, STRLEN); // Hold onto the line for the next time around
	}
}