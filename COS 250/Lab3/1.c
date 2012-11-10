/* Christopher Lenk for COS 250
   Prints out all the prime numbers from 1-100 */

#include <stdio.h>

#define UPPER 100 /* The upper end of the range of prime numbers to find */

int main(void) {
	int prime;
	int i;
	for (i = 1; i <= UPPER; i++) {
		if (i == 1 || i == 2) { // The smallest prime numbers
			pr(i);
		} else {
			prime = 1; // True
			int j; 
			for (j = i-1; j > 1; j--) {
				if (i % j == 0) { // Number is not prime!
					prime = 0;
				}
			}
			if (prime == 1) {
				pr(i);
			}
		}
	}
}

// Prints out the number passed in on a new line
int pr(int n) {
	printf("%d \n", n);
}