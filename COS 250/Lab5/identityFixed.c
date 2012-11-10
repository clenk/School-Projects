/* Modified by Christopher Lenk for COS 250 */

#include <stdio.h>
#define SIZE 10

void set_identity(int matrix[SIZE][SIZE]) {
	int i;
	for (i = 0; i < SIZE; i++) { // Go through rows
		int j;
		for (j = 0; j < SIZE; j++) { // Go through columns
			if (i == j) { // Current spot is on the diagonal
				matrix[i][j] = 1;
			} else {
				matrix[i][j] = 0;
			}
		}
	
	}
}
       
void print_identity(int matrix[SIZE][SIZE]) {
	int i;
	for (i = 0; i < SIZE; i++) { // Go through rows
		int j;
		for (j = 0; j < SIZE; j++) { // Go through columns
			printf("%d ", matrix[i][j]);
		}
		printf("%s", "\n");
	}
}

int test_identity(int matrix[SIZE][SIZE]) {
	int i;
	for (i = 0; i < SIZE; i++) { // Go through rows
		int j;
		for (j = 0; j < SIZE; j++) { // Go through columns
			if (i == j) { // Current spot is on the diagonal
				if (matrix[i][j] != 1) {
					return 0; // Not an identity!
				}
			} else {
				if (matrix[i][j] != 0) {
					return 0; // Not an identity!
				}
			}
		}
	}
	
	return 1; // If made it this far, no problems found so it is an identity
}

main() {
    int matrix[SIZE][SIZE];

    set_identity(matrix);
    print_identity(matrix);
    if(test_identity(matrix)) {
        printf("is identity matrix\n");
    } else {
        printf("is NOT identity matrix\n");
    }
    matrix[0][3] = 1;
    print_identity(matrix);
    if(test_identity(matrix)) {
        printf("is identity matrix\n");
    } else {
        printf("is NOT identity matrix\n");
    }
}
