/* Christopher Lenk for COS 250
   Prints out the input. */

#include <stdio.h>
#include <stdlib.h>

int main( void ) {
	char c = getchar();
	while (c > 0) {
		printf( "%c", c );
		c = getchar();
	}
}


