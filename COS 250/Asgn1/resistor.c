/* Christopher Lenk for COS 250
   "prompts the user for the 4 bands and prints out the resistance
   as a range in ohms or kilo-ohms as appropriate." */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define SIZE 13 // The number of color codes in the table
#define STRINGLENGTH 8 // Max. length of color names

// Set up the resistor table; -9 indicates null
char *colors[SIZE][STRINGLENGTH] = {{"black\n"}, {"brown\n"}, {"red\n"}, {"orange\n"}, {"yellow\n"}, {"green\n"}, {"blue\n"}, {"violet\n"}, {"grey\n"}, {"white\n"}, {"gold\n"}, {"silver\n"}, {"none\n"}};
int digits[SIZE] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -9, -9, -9};
int multipliers[SIZE] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, 0};
double tolerances[SIZE] = {-9, .01, .02, -9, -9, .005, .0025, .001, .0005, -9, .05, .1, .2};

char str[STRINGLENGTH];
	
// Variables to hold the input colors
char band1[STRINGLENGTH];
char band2[STRINGLENGTH];
char band3[STRINGLENGTH];
char band4[STRINGLENGTH];
	
int tens;
int ones;
int multiplier;
double tolerance;
unsigned long int value; // Used in computing the resistance
double upper; // Upper limit of the resistance range
double lower; // Lower limit of the resistance range

// Returns the index of the given string in the colors array
int getindex(char strg[]) {
	int i;
	for (i=0; i < SIZE; i++) {
		if (strncmp(strg, *colors[i], STRINGLENGTH) == 0) {
			return i;
		}
	}
	return -1; // Return value if the index is not found (index can't be negative so it's okay)
}

// Asks the user for the colors of the bands on the resistor
// Returns: 0 if successful, 1 if error occured, -1 if "done" entered
int getinput(void) {
	puts("\nEnter first band color (type \"done\" to exit program): ");
	fgets(str, STRINGLENGTH, stdin);
	strncpy(band1, str, STRINGLENGTH);
	int index1 = getindex(band1);
	// Make sure the color was found and it has a corresponding value in the correct array
	if (index1 != -1 && digits[index1] != -9) {
		tens = digits[index1];
	} else if (strncmp(str, "done\n", STRINGLENGTH) == 0) {
		return -1;
	} else {
		puts("Error: Please enter a color used as a digit in resistors (use only lowercase).");
		return 1;
	}
		
	puts("Enter second band color: ");
	fgets(str, STRINGLENGTH, stdin);
	strncpy(band2, str, STRINGLENGTH);
	int index2 = getindex(band2);
	if (index2 != -1 && digits[index2] != -9) {
		ones = digits[index2];
	} else if (strncmp(str, "done\n", STRINGLENGTH) == 0) {
		return -1;
	} else {
		puts("Error: Please enter a color used as a digit in resistors (use only lowercase).");
		return 1;
	}
		
	puts("Enter third band color: ");
	fgets(str, STRINGLENGTH, stdin);
	strncpy(band3, str, STRINGLENGTH);
	int index3 = getindex(band3);
	if (index3 != -1 && multipliers[index3] != -9) {
		multiplier = multipliers[index3];
	} else if (strncmp(str, "done\n", STRINGLENGTH) == 0) {
		return -1;
	} else {
		puts("Error: Please enter a color used as a multiplier (use only lowercase).");
		return 1;
	}
		
	puts("Enter fourth band color: ");
	fgets(str, STRINGLENGTH, stdin);
	strncpy(band4, str, STRINGLENGTH);
	int index4 = getindex(band4);
	if (index4 != -1 && tolerances[index4] != -9) {
		tolerance = tolerances[index4];
	} else if (strncmp(str, "done\n", STRINGLENGTH) == 0) {
		return -1;
	} else {
		puts("Error: Please enter a color used as a tolerance (use only lowercase).");
		return 1;
	}
	
	return 0;
}

// Calculates the resistance range
void calculate(void) {
	value = (((tens * 10) + ones) * pow(10, multiplier)); // Get the base resistance
		
	upper = (value + (tolerance * value));
	lower = (value - (tolerance * value));
}

// Prints out the resistance range
void print(void) {
	printf("%s", "Resistance Range: ");
	
	if (lower > 999) { // If the range is big enough, convert it into kilo-ohms
		printf("%.2f to %.2f kilo-ohms \n", (lower/1000), (upper/1000));
	} else if (lower < 1) { // If the range is small enough, add more decimal places
		printf("%.4f to %.4f ohms \n", lower, upper);
	} else { // Otherwise just leave it in ohms
		printf("%.2f to %.2f ohms \n", lower, upper);
	}
}

// MAIN
int main(void) {
	int stop = 1;
	
	while (stop != -1) {
		stop = getinput();
		
		if (!stop) {
		// Calculate the resistance range and print it out
		calculate();
		print();
		}
	}
}
