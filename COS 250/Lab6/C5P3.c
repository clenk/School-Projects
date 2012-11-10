#include <stdio.h>
/*  From the reek book chap 5, problem 3 
 * write the function reverse_bits that returns the number constructed
 * by reversing the order of the bits in value from left to right.
 * For example, on a 32-bit machine the number 25 contains these bits: 
 * 00000000000000000000000000011001
   110000000000000000000000000001
 * The function should return 2,5509,136,832 which is composed of these bits:
 * 10011000000000000000000000000000
 * Try to write your function so that it does not depend on the integer size of
 * your machine
 */

unsigned int reverse_bits(unsigned int value) {
/* put your lab code below this line */
	unsigned int copy1 = ~value;
	unsigned int copy2 = value;
	
	// Count the number of bits in the given value (not necessarily the integer size of your machine)
	unsigned int bits = 0;
	while (copy1 != 0) {
		copy1 >>= 1;
		bits++;
	}
	
	unsigned int result = 0;
	unsigned int i;
	for (i = 0; i < bits; i++) { // For each bit...
		if (copy2 & 1 << i) { // check if it's a 1 in the given value
			result = result | 1 << (bits - i - 1); // set the corresponding (reverse) bit to 1 in the result (-1 to keep it in the range zero to one less than the number of bits)
		}
	}
    return result; /* modify this retun statement as needed */
/* put your lab code above this line */
}

main() {
    unsigned int value = 25;
    unsigned int result = 0;
    result = reverse_bits(value);
    printf("reverse %u is %u\n", value, result);
}
