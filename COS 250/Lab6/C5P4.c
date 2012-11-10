#include <stdio.h>
/*
 * Write a set of functions that implement an array of btis 
 * The first argument in each of thes functions is a character array in which the
 * bits are actually stored.  The second argument identifies the bit to be 
 * accessed; it is up to the caller to ensure that this value is not too large 
 * for the array being used.
 */

/* set_bit sets the specified bit to one */
void set_bit(char bit_array[], unsigned bit_number) {
    bit_array[bit_number] = 1;
}

/* clear_bit clears the specified bit to zero */
void clear_bit(char bit_array[], unsigned bit_number) {
    bit_array[bit_number] = 0;
}

/* assign_bit sets the bit to zero if the value is zero, otherwise it sets the bit to one. */
void assign_bit(char bit_array[], unsigned bit_number, int value) {
    if (value == 0) {
		bit_array[bit_number] = 0;
	} else {
		bit_array[bit_number] = 1;
	}
}

/* the last function returns true if the specified bit is nonzero, else false */
int test_bit(char bit_array[], unsigned bit_number) {
    if (bit_array[bit_number] != 0) {
		return 1;
	} else {
		return 0;
	}
}

main() {
    char bits[5];
    int pick = 20;
    if(test_bit(bits,pick) == 1) {
        printf("bit %d is 1\n", pick);
    } else {
        printf("bit %d is 0\n", pick);
    }
    set_bit(bits, pick);
    if(test_bit(bits,pick) == 1) {
        printf("bit %d is 1\n", pick);
    } else {
        printf("bit %d is 0\n", pick);
    }
    clear_bit(bits, pick);
    if(test_bit(bits,pick) == 1) {
        printf("bit %d is 1\n", pick);
    } else {
        printf("bit %d is 0\n", pick);
    }
    assign_bit(bits, pick, -1);
    if(test_bit(bits,pick) == 1) {
        printf("bit %d is 1\n", pick);
    } else {
        printf("bit %d is 0\n", pick);
    }
    assign_bit(bits, pick, 0);
    if(test_bit(bits,pick) == 1) {
        printf("bit %d is 1\n", pick);
    } else {
        printf("bit %d is 0\n", pick);
    }
    
}
