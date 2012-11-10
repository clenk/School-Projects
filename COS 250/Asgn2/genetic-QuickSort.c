/* Christopher Lenk for COS 250
   Implements a simple genetic algorithm to randomly generate sentences
   until a pre-selected target sentence is generated.
   Implements a quicksort (based on pseudocode from Wikipedia). */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUMCANDIDATES 16 // Number of candidate sentences to generate, should be divisible by 2
#define TARGETLEN 20 // Length of the target sentence (number of characters +1)
#define TARGET "the quick brown fox" // The target sentence
#define MUTRATE 1 // Mutation rate
#define ALLOWABLECHARS 27 // The number of allowable characters

long ranks[NUMCANDIDATES]; // Stores the ranks of each sentence
char sentences[NUMCANDIDATES][TARGETLEN]; // Array of generated sentences
char characters[ALLOWABLECHARS] = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}; // Allowed characters
char *target = TARGET; // The target sentence
long genCount; // The number of generations gone through so far

// Prints out all the current candidate sentences
void print() {
	long i;
	for (i = 0; i < NUMCANDIDATES; i++) { // Go through all the sentences
		char *str = sentences[i];
		printf("%d:%s:", ranks[i], str);
		printf("%s", "\n");
	}
	
	printf("\n"); // For readability, skip a line
}

// Swaps the elements of the array at the two given indices
void swapLong(long arr[], long a, long b){
	long tmp = arr[a];
	arr[a] = arr[b];
	arr[b] = tmp;
}
void swapChar(char arr[][TARGETLEN], long a, long b){
	char tmp[TARGETLEN];
	strncpy(tmp, arr[a], TARGETLEN);
	strncpy(arr[a], arr[b], TARGETLEN);
	strncpy(arr[b], tmp, TARGETLEN);
}

// Sets up global arrays with the right values
void init() {
	srand(time(NULL)); // Get a new seed for the random number generator
	genCount = 0;

	long i;
	for (i = 0; i < NUMCANDIDATES; i++) { // Fill ranks with starter values
		ranks[i] = 0;
	}
	
	long j;
	for (j = 0; j < NUMCANDIDATES; j++) { // Generate random starter sentences
		long k;
		for (k = 0; k < TARGETLEN-1; k++) {
			sentences[j][k] = characters[(rand() % ALLOWABLECHARS)];
		}
		sentences[j][k] = '\0'; // Add the terminating character
	}
}

// Determines how close the candidate sentences are to the target sentence (count how many letters are in the right place)
void rank() {
	long rank; // The number of characters in the right place
	
	long i;
	for (i = 0; i < NUMCANDIDATES; i++) { // Go through each sentence
		rank = 0; // Start the rank count
		
		long j;
		for (j = 0; j < TARGETLEN-1; j++) { // Go through each character
			if (sentences[i][j] == target[j]) {
				rank++;
			}
		}
		ranks[i] = rank; // Store the rank for this sentence
	}
}

// Needed for quicksort; partitions the part of the array between the first two params
// by moving everything less than the third param before the pivot, and those greater
// after it, and returns the final position of the pivot element
long partition(long left, long right, long pivotIndex) {
	// Store rank value (what we're sorting by) at the pivot point
	long pivRank = ranks[pivotIndex];
	
	// Move pivots to the end of their array
	swapLong(ranks, pivotIndex, right);
	swapChar(sentences, pivotIndex, right);
	
	// Move things around based on how they compare to the pivot point
	long storeIndex = left;
	long i;
	for (i = left; i < right ;i++) {
		if (ranks[i] > pivRank) {
			swapLong(ranks, i, storeIndex);
			swapChar(sentences, i, storeIndex);
			
			storeIndex++;
		}
	}
	
	// Move pivots to their final places
	swapLong(ranks, storeIndex, right);
	swapChar(sentences, storeIndex, right);
	
	return storeIndex;
}

// Reorders sentences so the best are at the top with a quicksort
void sort(long left, long right) {
	if (left < right) { // Make sure there's something to sort
		long pivot = (left + right) / 2; // Pick a pivot point
		
		// Do swaps and get final position of the pivot
		long newPivot = partition(left, right, pivot);
		
		// Recursively sort elements smaller than the pivot
		sort(left, newPivot-1);
 
        // Recursively sort elements at least as big as the pivot
		sort(newPivot+1, right);
	}
}

// Breeds sentences together to generate new ones
void breed() {
	// Select a random spot in the sentence, -2+1 to avoid the terminating character and get a number
	// between the two endpoints (so at least one character will get changed with the breed)
	long breedSpot = ((rand() % (TARGETLEN - 2)) + 1);
	char child1[TARGETLEN];
	char child2[TARGETLEN];
	
	long half = (NUMCANDIDATES / 2); // Halfway mark in the array of sentences
	long i;
	for (i = 0; i < half; i+=2) {
		// Combine the first part of first sentence with the second part of second sentence,
		// and combine the first part of the second sentence with the second part of the first sentence 
		long j;
		for (j = 0; j < TARGETLEN; j++) {
			if (j < breedSpot) { 
				child1[j] = sentences[i][j];
				child2[j] = sentences[i+1][j];
			} else {
				child1[j] = sentences[i+1][j];
				child2[j] = sentences[i][j];
			}
		}
		
		// Put the newly-bred sentences in the bottom half (lowest ranked) of our collection of sentences
		strncpy(sentences[i+half], child1, TARGETLEN);
		strncpy(sentences[i+half+1], child2, TARGETLEN);
	}
}

// Applies random changes to the sentences
void mutate() {
	long numMuts = MUTRATE * NUMCANDIDATES; // Number of mutations that should occur for this generation
	
	long i;
	for (i = 0; i < numMuts; i++) { // Do the right number of mutations
		long sentce = (rand() % NUMCANDIDATES); // Pick a random sentence
		long chrctr = (rand() % (TARGETLEN - 1)); // Pick a random character in that sentence (but not the termination character!)
		long letter = (rand() % ALLOWABLECHARS); // Pick a random letter
		
		// Mutate!
		sentences[sentce][chrctr] = characters[letter];
	}
}

// Checks to see if the target sentence has been found
// Returns -1 if it has not, otherwise returns the index of the generated sentence
long test() {
	long i;
	for (i = 0; i < NUMCANDIDATES; i++) { // Go through all the sentences
		if (!strncmp(sentences[i], TARGET, TARGETLEN)) { // If they're equal we found it
			return i;
		} else { // Otherwise we failed
			return -1;
		}
	}
}

// MAIN
long main(void) {
	init();
	
	rank();
	sort(0, NUMCANDIDATES-1);
	while (ranks[0] != TARGETLEN-1) { // Loop till each character in the target sentence is correct
		breed();
		mutate();
		genCount++;
		
		rank();
		sort(0, NUMCANDIDATES-1);
		print();
	}
	printf("Sentence found after %d generations: %s\n", genCount, sentences[0]);
}
