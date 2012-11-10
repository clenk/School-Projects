/* Modified by Christopher Lenk for COS 250 */

#include <stdio.h>
#include <float.h>

#define NUMBRACKETS 5 // The number of different tax brackets (length of the array)

struct tax_bracket {
    double min;  /* lowest income in bracket */
    double max;  /* highest income in bracket */
    double taxBase; /* base tax amount for bracket */
    double pct;  /* tax percentage for this bracket */
};

/* create table here using struct tax_bracket to declare an arraytable of 
 * type tax_bracket and initialize with values
 */
 
struct tax_bracket arr[] = {{.min = 0, .max = 23350, .taxBase = 0, .pct = 15},
						{.min = 23350, .max = 56550, .taxBase = 3502.50, .pct = 28},
						{.min = 56550, .max = 117950, .taxBase = 12798.50, .pct = 31},
						{.min = 117950, .max = 256500, .taxBase = 31852.50, .pct = 36},
						{.min = 256500, .max = DBL_MAX, .taxBase = 81710.50, .pct = 39.6}};

void print_table() {
	printf("%s\t%s\t%s\t%s\t\n", "Taxable Threshold", "Tax Limit", "Base Tax", "Tax Percentage"); // Column headers
	
	int i;
	for (i = 0; i < NUMBRACKETS; i++) {
		printf("%9.2f\t\t", arr[i].min);
		if (arr[i].max == DBL_MAX) { // Special case for no max value
			printf("%s", "infinite\t");
		} else {
			printf("%9.2f\t", arr[i].max);
		}
		printf("%9.2f\t", arr[i].taxBase);
		printf("%4.1f%%", arr[i].pct);
		printf("%s", "\n");
	}
	
	printf("%s", "\n"); // Skip a line for readability
}

double single_tax(double income) {
	if (income == 0) { // Special case of $0
		return 0;
	}
	
	int i;
	for (i = 0; i < NUMBRACKETS; i++) {
		if (income < arr[i+1].min) {
			return (arr[i].taxBase + (arr[i].pct / 100) * (income - arr[i].min));
		}
	}

    return -1; // if this is returned, there was an error somewhere
}

main() {
    double income;
    print_table();
    income = 0; 
    printf("tax on $%.2f is $%.2f\n", income, single_tax(income));
    income = 23350; 
    printf("tax on $%.2f is $%.2f\n", income, single_tax(income));
    income = 23400; 
    printf("tax on $%.2f is $%.2f\n", income, single_tax(income));
    income = 120000; 
    printf("tax on $%.2f is $%.2f\n", income, single_tax(income));
    income = 300000; 
    printf("tax on $%.2f is $%.2f\n", income, single_tax(income));
}
