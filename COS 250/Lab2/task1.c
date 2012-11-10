/* Christopher Lenk for COS 250
   Prints out a table of Fahrenheit temperatures and their Celcius equivalents */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUM_COLS 16 /* Number of temperatures to compute */
#define LOW_F 0 /* Lowest Fahrenheit temperature to compute*/
#define HI_F 300 /* Highest Fahrenheit temperature to compute*/
#define INCR 20 /* # of degrees to increment Fahrenheit values by */

double convert( int f ); 

int main( void ) {
	int fahr[NUM_COLS]; /* Holds Fahrenheit values */
	double cels[NUM_COLS]; /* Holds Celsius values */
	
	/* Fill the Fahrenheit array */
	int i;
	for (i = 0; i < NUM_COLS; i++) {
		fahr[i] = (i*INCR);
	}
	
	/* Fill the Celcius array */
	int j;
	for (j = 0; j < NUM_COLS; j++) {
		cels[j] = convert(j*INCR);
	}
	
	/* Print out the arrays */
	int k;
	for (k = 0; k < NUM_COLS; k++) {
		printf( "%d \t %.1f \n", fahr[k], cels[k] );
	}	
}

/* Returns the degrees Celcius equal to the degrees Fahrenheit passed in */
double convert( int f ) {
	return ( (5.0 / 9.0) * (f - 32) );
}