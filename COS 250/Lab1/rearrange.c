#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_COLS 20 	/* max number of columns to handle */
#define MAX_INPUT 1000	/* max length of input/output lines */

int	read_col_numbers( int cols[], int max);
void	rearrange( char *output, char const *input, int n_cols, int const cols[] );

int main( void ) {
	int	n_cols;			/* number of columns to handle */
	int	cols[MAX_COLS];	/* array of columns to handle */
	char	input[MAX_INPUT];	/* array of input lines */
	char	output[MAX_INPUT];	/*Array of output lines */

	/* Read the list of column numbers */
	n_cols = read_col_nums( cols, MAX_COLS );

	/* Read, process, and print the remaining lines of input */
	while( gets( input ) != NULL ){
		printf( "Original input : %s\n", input );
		rearrange( output, input, n_cols, cols );
		printf( "Rearranged line : %s\n", output );
	}

	return EXIT_SUCCESS;
}

/* Read the list of column numbers, ignoring any beyond the specified maximum */
int read_col_nums( int cols[], int max ) {
	int	num = 0;
	int	ch;

	/* Get the numbers but stop at EOF or a negative number */
	while ( num < max && scanf( "%d", &cols[num] ) == 1 && cols[num] >= 0 )
		num += 1;

	/* Ensure an even number of inputs, for pairing */
	if( num % 2 != 0) {
		puts( "Last column number is not paired." );
		exit( EXIT_FAILURE );
	}

	/* Discard the rest of the line that contained the final number. */
	while( (ch = getchar()) != EOF && ch != '\n' )
		;

	return num;
}

/* Process a line of input by concatenating the characters from the indicated columns. The output line is then NUL terminated */
void rearrange( char *output, char const *input, int n_cols, int const cols[] ) {
	int	col;		/* subscript for columns array */
	int	output_col;	/* output column counter */
	int	length;		/* length of the input line */

	length = strlen( input );
	output_col = 0;

	/* Process each pair of column numbers */
	for( col = 0; col < n_cols; col += 2 ) {
		int	nchars = cols[col + 1] - cols[col] + 1;

		/* If input line isn't this long or output array is full, then done */
		if( cols[col] >= length || output_col == MAX_INPUT - 1 )
			break;

		/* If no room in output array, only copy what'll fit */
		if( output_col + nchars > MAX_INPUT - 1 )
			nchars = MAX_INPUT - output_col - 1;

		/* Copy relevant data */
		strncpy( output + output_col, input + cols[col], nchars );
		output_col += nchars;
	}

	output[output_col] = '\0';
}
