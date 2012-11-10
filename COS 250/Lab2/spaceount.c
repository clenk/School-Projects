/* Christopher Lenk for COS 250
   Counts the number of whitespaces in the input and prints the count. */

#include <stdio.h>
#include <stdlib.h>

int main( void ) {
	int count = 0;
	int c = getchar();
	while (c > 0) {
		if (c == ' ' || c == '\t' || c == '\n') {
			count++;
		}
		c = getchar();
	}
	
	printf( "%d", count);
}