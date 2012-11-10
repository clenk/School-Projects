/* Christopher Lenk for COS 250
   Implements a simple genetic algorithm to randomly generate sentences
   until a pre-selected target sentence is generated */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER 1000 // Max size of commandline input string
#define NUMCANDIDATES 1000 // Number of candidate sentences to generate, should be divisible by 2
#define MUTRATE 1 // Mutation rate
#define ALLOWABLECHARS 27 // The number of allowable characters // (number of characters +1)

long targetLen; // Length of the target sentence
char *target; // The target sentence

long ranks[NUMCANDIDATES]; // Stores the ranks of each sentence
char sentences[NUMCANDIDATES][BUFFER]; // Array of generated sentences
char characters[ALLOWABLECHARS] = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}; // Allowed characters
long genCount; // The number of generations gone through so far

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
		for (k = 0; k < targetLen-1; k++) {
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
		for (j = 0; j < targetLen-1; j++) { // Go through each character
			if (sentences[i][j] == target[j]) {
				rank++;
			}
		}
		ranks[i] = rank; // Store the rank for this sentence
	}
}

// Swaps the elements of the array at the two given indices
void swapLong(long arr[], long a, long b){
	long tmp = arr[a];
	arr[a] = arr[b];
	arr[b] = tmp;
}
void swapChar(char arr[][targetLen], long a, long b){
	char tmp[targetLen];
	strncpy(tmp, arr[a], targetLen);
	strncpy(arr[a], arr[b], targetLen);
	strncpy(arr[b], tmp, targetLen);
}

// Reorders sentences so the best are at the top with a bubble sort
void sort() {
	long swapped = 1;
	while (swapped) { // Repeat until a swap hasn't occured on the last pass
		swapped = 0;
		long i;
		for (i = 1; i < NUMCANDIDATES; i++) { // Go through each rank
			if (ranks[i-1] < ranks[i]) { // Swap so highest ranks come first in the array
				// Swap both the ranks and the sentences
				/*long tmp = ranks[i];
				char tmpStr[targetLen];
				strncpy(tmpStr, sentences[i], targetLen);
				ranks[i] = ranks[i-1];
				strncpy(sentences[i], sentences[i-1], targetLen);
				ranks[i-1] = tmp;
				strncpy(sentences[i-1], tmpStr, targetLen);*/
				swapLong(ranks, i, i-1);
				swapChar(sentences, i, i-1);
				
				swapped = 1; // A swap has occured
			}
		}
	}
}

// Breeds sentences together to generate new ones
void breed() {
	// Select a random spot in the sentence, -2+1 to avoid the terminating character and get a number
	// between the two endpoints (so at least one character will get changed with the breed)
	long breedSpot = ((rand() % (targetLen - 2)) + 1);
	char child1[targetLen];
	char child2[targetLen];
	
	long half = (NUMCANDIDATES / 2); // Halfway mark in the array of sentences
	long i;
	for (i = 0; i < half; i+=2) {
		// Combine the first part of first sentence with the second part of second sentence,
		// and combine the first part of the second sentence with the second part of the first sentence 
		long j;
		for (j = 0; j < targetLen; j++) {
			if (j < breedSpot) { 
				child1[j] = sentences[i][j];
				child2[j] = sentences[i+1][j];
			} else {
				child1[j] = sentences[i+1][j];
				child2[j] = sentences[i][j];
			}
		}
		
		// Put the newly-bred sentences in the bottom half (lowest ranked) of our collection of sentences
		strncpy(sentences[i+half], child1, targetLen);
		strncpy(sentences[i+half+1], child2, targetLen);
	}
}

// Applies random changes to the sentences
void mutate() {
	long numMuts = MUTRATE * NUMCANDIDATES; // Number of mutations that should occur for this generation
	
	long i;
	for (i = 0; i < numMuts; i++) { // Do the right number of mutations
		long sentce = (rand() % NUMCANDIDATES); // Pick a random sentence
		long chrctr = (rand() % (targetLen - 1)); // Pick a random character in that sentence (but not the termination character!)
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
		if (!strncmp(sentences[i], target, targetLen)) { // If they're equal we found it
			return i;
		} else { // Otherwise we failed
			return -1;
		}
	}
}

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

// MAIN
int main(void) {
	// Get string from commandline
	fputs("Please provide a target string: ", stdout);
	char *temp;
	fgets(temp, BUFFER, stdin); // Why does this line give a segfault when I put this block of code inside init()?
	targetLen = strlen(temp);
	target = malloc(targetLen * sizeof(char));
	strncpy(target, temp, targetLen-1); // -1 to discard return character
	printf("%s", target);
	
	init();
	
	rank();
	sort(0, NUMCANDIDATES-1);
	while (ranks[0] != targetLen-1) { // Loop till each character in the target sentence is correct
		breed();
		mutate();
		genCount++;
		
		rank();
		sort(0, NUMCANDIDATES-1);
		print();
	}
	printf("Sentence found after %d generations: %s\n", genCount, sentences[0]);
}
