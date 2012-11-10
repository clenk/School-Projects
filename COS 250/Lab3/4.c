/* Christopher Lenk for COS 250
   Demonstrates a function that removes excess whitespaces from a given string */

#include <string.h>

#define STRLEN 128 // Max length of a string

// Replaces every run of any number of whitespaces with a single space
void deblank(char string[]) {
	char str[STRLEN]; // Intermediate string
	int white = 0; // Boolean: 1 (true) if the last character was whitespace
	
	int i;
	int j = 0;
	for (i = 0; string[i] != '\0'; i++) {
		// Check for whitespace characters
		if (string[i] != ' ' && string[i] != '\t' && string[i] != '\n') {
			str[j] = string[i];
			white = 1;
			j++;
		} else if (white) { // If the current character isn't whitespace but the last one was, insert a space
			str[j] = ' ';
			j++;
			white = 0;
		}
	}
	
	str[j+1] = '\0'; // NUL-terminate the string
	strncpy(string, str, STRLEN); // Copy the deblanked string into the original string
}

// MAIN (for testing)
int main(void) {
	char str1[STRLEN] = "	  		First   \t  Last				    \n\n\n";
	
	deblank(str1);
	
	puts(str1);
}