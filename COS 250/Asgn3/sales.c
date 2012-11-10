// Christopher Lenk for COS 250

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "sales.h"

#define LINE_LENGTH 80 // Max number of characters allowed on a single line in the data files

int numSlots; // The number of spots in memory for employees in our sales data (always 1 more than the number of employees actually in it)

// Checks if an employee with the given name exists in the given array of employees
int empExists(Emp *arr, char *nam) {
	int i;
	for (i = 0; i < numSlots; i++) {
		if (!strncmp(arr[i].name, nam, MAX_NAME)) {
			return i;
		}
	}
	
	return -1;
}

// Prints the given array of employees
void print(Emp *arr, int size, char *tag) {
	int monthlyTotals[NUM_MONTHS] = {0};
	int grandTotal = 0;

	// Print the headings
	printf("Sales Report (%s)\t\t\t\t\tNumber of Employees: %d\n", tag, size);
	puts("Name\t\tMonth Sales\t\t\t\t\tTotal Sales");
	puts("-------------------------------------------------------------------------------");
	
	// Go through the data for each employee
	int i;
	for (i = 0; i < size; i++) {
		// Print the name
		if (strlen(arr[i].name) > 7) {
			printf("%s\t", arr[i].name);
		} else {
			printf("%s\t\t", arr[i].name);
		}
		
		// Print the person's monthly totals
		int j;
		for (j = 0; j < NUM_MONTHS; j++) {
			printf("%3d ", arr[i].monthSales[j]);
			monthlyTotals[j] += arr[i].monthSales[j];
		}
		
		// Print the the person's total for that year
		printf("\t%5d\n", arr[i].total);
		grandTotal += arr[i].total;
	}
	
	// Print totals for all employees
	puts("-------------------------------------------------------------------------------");
	printf("Total Sales\t");
	int k;
	for (k = 0; k < NUM_MONTHS; k++) {
		printf("%3d ", monthlyTotals[k]);
	}
	printf("\t%5d\n\n", grandTotal);
}

// Comparator to compares two strings for sorting
int compareStr(const void *a, const void *b) {
	Emp *ax = (Emp *)a;
	Emp *bx = (Emp *)b;
	return strncmp(ax->name, bx->name, MAX_NAME);
}

// Comparator to compares two integers for sorting
int compareInt(const void *a, const void *b) {
	Emp *ax = (Emp *)a;
	Emp *bx = (Emp *)b;
	return (ax->total - bx->total);
}

// Copies one array of Emp structs to another
void copy(Emp *dest, Emp *src, int size) {
	int i;
	for (i = 0; i < size; i++) {
		strncpy(dest[i].name, src[i].name, MAX_NAME);
		int j;
		for (j = 0; j < NUM_MONTHS; j++) {
			dest[i].monthSales[j] = src[i].monthSales[j];
		}
		dest[i].total = src[i].total;
	}
}

// MAIN
int main( int argc, char *argv[] ) {
	// Error-check the command-line input
	if (argc != 2) {
		puts("Error: Must have exactly 1 commandline argument: the filename of the sales data file.");
		return 1;
	}
	
	// Open the file stream
	FILE *file;
	file = fopen(argv[1], "r");
	
	// Make sure file was opened okay
	if (file == NULL) {
		puts("Error: Specified file could not be opened.");
		return 1;
	}
	
	// Get space for the sales data
	Emp *sales_data = (Emp*) malloc(sizeof(Emp));
	numSlots = 1;
	
	// Loop through the lines in the file
	char line[LINE_LENGTH];
	while (!feof(file)) {
		if (fgets(line, LINE_LENGTH, file) != NULL) { // Get the next line
			
			// Get the fields from the line
			char *name;
			char *monthStr;
			char *amountStr;
			name = strtok(line, " /\n");
			monthStr = strtok(NULL, " /\n");
			int month = atoi(monthStr);
			strtok(NULL, " /\n"); // Chew through the 'day' field
			amountStr = strtok(NULL, " /\n");
			int amount = atoi(amountStr);
			
			if (name == NULL || monthStr == NULL || amountStr == NULL) {
				puts("Error: Data file incorrectly formatted.");
				return 1;
			}
			
			int slot = empExists(sales_data, name);
			if (slot != -1) { // If the employee already exists in the data, add the amount
				sales_data[slot].monthSales[month-1] += amount;
				sales_data[slot].total += amount;
			} else { // If the employee isn't in our data yet, make a new entry
				Emp *newPtr = (Emp*) realloc(sales_data, sizeof(Emp)*(numSlots));
				if (newPtr != NULL) { // Check for reallocation errors
					sales_data = newPtr;
					
					strncpy(sales_data[numSlots-1].name, name, MAX_NAME);
					sales_data[numSlots-1].monthSales[month-1] += amount;
					sales_data[numSlots-1].total += amount;
					numSlots++;
				} else {
					puts("Error: Realloc failed.");
					return 1;
				}
			}
			
		}
	}
	numSlots--; // Fix the count to make it accurate
	
	// Make copies of the pointers to the data so we can sort them
	Emp *byName = (Emp*) malloc(sizeof(Emp)*numSlots);
	Emp *byTotal = (Emp*) malloc(sizeof(Emp)*numSlots);
	copy(byName, sales_data, numSlots);
	copy(byTotal, sales_data, numSlots);
	
	// Sort the reports
	qsort(byName, numSlots, sizeof(Emp), compareStr);
	qsort(byTotal, numSlots, sizeof(Emp), compareInt);
	
	// Print the reports
	print(sales_data, numSlots, "Unsorted");
	print(byName, numSlots, "By Name");
	print(byTotal, numSlots, "By Total");
	
	// Housekeeping and shutdown
	free(sales_data);
	fclose(file);
	return 0;
}