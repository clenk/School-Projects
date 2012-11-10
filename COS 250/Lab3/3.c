/* Christopher Lenk for COS 250
   Demonstrates a function that extracts a substring from a string */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Copies the string starting 'start' characters after the beginning of
   'src' string into the 'dst' string, but no more than 'len' characters
   then returns the length of the string put into 'dst' */
int substr(char dst[], char src[], int start, int len) {
	int length = 0;
	int i;
	
	// Check for negative-value errors
	if (start < 0 || len < 0) {
		dst = "";
		return 0;
	}
	
	// Otherwise proceed normally
	for (i = start; src[i] != '\0'; i++) {
		//printf("%s\n", src[i]);
		if (length < len) {
			dst[length] = src[i];
			length++;
		}
	}
	dst[length] = '\0'; // NUL-terminate the string
	
	return length;
}

// MAIN (for testing)
int main(void) {
	char str1[56] = "In.the.beginning.God.created.the.heavens.and.the.Earth.";
	char str2[56];
	
	printf("%s\n", str1);
	printf("%s\n", str2);
	
	printf("%d\n", substr(str2, str1, 75, 4));
	
	printf("%s\n", str1);
	printf("%s\n", str2);
}
