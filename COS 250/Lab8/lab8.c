// By Christopher Lenk for COS 250

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ALPHA 0
#define BRAVO 1
#define CHARLIE 2
#define DELTA 3

// The functions that print the name of the function and the string passed in
int alpha(char *string) {
	printf("Alpha: %s\n", string);
	return 1;
}

int bravo(char *string) {
	printf("Bravo: %s\n", string);
	return 1;
}

int charlie(char *string) {
	printf("Charlie: %s\n", string);
	return 1;
}

int delta(char *string) {
	printf("Delta: %s\n", string);
	return 1;
}

// The jump table
int (*functs[])(char *string) = {alpha, bravo, charlie, delta};

// MAIN
int main( int argc, char *argv[] ) {
	// Error-check the command-line input
	if (argc != 3) {
		puts("Error: Must have 2 commandline arguments: the lowercase name of the function to be called, and the string to pass into it");
		return 0;
	}
	
	// Determine the function to call
	int f = -1;
	if (!strcmp(argv[1], "alpha")) {
		f = ALPHA;
	} else if (!strcmp(argv[1], "bravo")) {
		f = BRAVO;
	} else if (!strcmp(argv[1], "charlie")) {
		f = CHARLIE;
	} else if (!strcmp(argv[1], "delta")) {
		f = DELTA;
	}
	
	// Call the function if one was found
	if (f > -1) {
		functs[f](argv[2]);
	} else {
		puts("Error: Function name must be one included in the program!");
		return 0;
	}
	
	return EXIT_SUCCESS;
}