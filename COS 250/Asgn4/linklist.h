/* 
	Implements a symbol table using an unordered linked list in C.
	Based on code in Pointers On C, by Kenneth A. Reek.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define FALSE 0
#define TRUE 1
#define MAX_SYM_LEN 9 // The maximum length of a symbol's name
#define INVALID -1

struct listNode {
	char *symbol;
	long int value;
	long int defined; // The address at which the symbol was defined
	int type; // 1 means dec, 2 means hex
	struct listNode *link;
	struct listNode *refs; // Head of the linked list of code references
};

typedef struct listNode node;

// Initialize the list with a head node
node *head;
node **headPtr = &head;

// Prints out the code references for the given node
void printRefs(node *n, FILE *out) {
	// Don't print anything if there aren't any references
	if (n == NULL) {
		fprintf(out, "\n");
		return;
	}
	
	node *cur;
	cur = n;
	while (cur != NULL) {
		fprintf(out, "%X, ", cur->value);
		cur = cur->link;
	}
	fprintf(out, "\n");
}

// Prints out the linked list, starting at the given node
void print(node **start, FILE *out) {
	if (*start == NULL) {
		fputs("\nSymbol Table is empty.\n", out);
		return;
	}
	
	fputs("\nSYMBOL TABLE\n", out);
	fputs("-----------------------------------------------\n", out);
	fputs(" Symbol   | Value | Defined | References \n", out);
	fputs("-----------------------------------------------\n", out);

	node *cur;
	cur = *start;
	while (cur != NULL) {
		fprintf(out, " %8s | ", cur->symbol);
		// Print the value correctly
		if (cur->value == -1) {
			fprintf(out, "      | ");
		} else if (cur->type == 1) {
			fprintf(out, "%04d  | ", cur->value);
		} else if (cur->type == 2) {
			fprintf(out, "%04X  | ", cur->value);
		}
		fprintf(out, "%X     | ", cur->defined);
		printRefs(cur->refs, out);
		cur = cur->link;
	}
}

// Adds a new node with the given symbol to the linked list
int add(node **start, char *new_symbol, long int val, long int def, int type) {
	node *oldhead = *start;
	
	if (*start == NULL) { // Case of empty list
		// Create a new node and assign it to the head pointer
		*start = (node *)malloc(sizeof(node));
		if (*start == NULL) { // Allocation failed
			printf("Memory allocation failed");
			return FALSE;
		}
		
		(*start)->symbol = malloc(MAX_SYM_LEN * sizeof(char));
		strncpy((*start)->symbol, new_symbol, MAX_SYM_LEN);
		(*start)->value = val;
		(*start)->defined = def;
		(*start)->type = type;
		
	} else { // Head is not null
		node *newN;
		
		// Traverse to the end of the list
		while ((*start)->link != NULL) {
            *start = (*start)->link;
        }
		
		// Allocate a new node and store the new symbol into it.
		newN = (node *)malloc(sizeof(node));
		if (newN == NULL) { // Allocation failed
			printf("Memory allocation failed");
			return FALSE;
		}
		newN->symbol = malloc(MAX_SYM_LEN * sizeof(char));
		strncpy(newN->symbol, new_symbol, MAX_SYM_LEN);
		newN->value = val;
		newN->defined = def;
		newN->type = type;
		
		(*start)->link = newN; // Insert the new node into the list
		*start = oldhead; // Reset the HEAD pointer
	}
	return TRUE;
}

// Finds the node with the given symbol
node * findSymbol(node **linkp, char *goal) {
	if (linkp == NULL) { // Check for empty list
		printf("%s\n", "Can't search an empty list!");
		return NULL;
	}

	node *cur;
	cur = *linkp;
	
	// Find the correct node
	while (cur != NULL) {
		if (strncmp(cur->symbol, goal, MAX_SYM_LEN) == 0) {
			return cur;
		}
		
		cur = cur->link;
	}
	return NULL;
}

// Finds the node defined at the given address
node * findAddr(node **linkp, long int goal) {
	if (linkp == NULL) { // Check for empty list
		printf("%s\n", "Can't search an empty list!");
		return NULL;
	}

	node *cur;
	cur = *linkp;
	
	// Find the correct node
	while (cur != NULL) {
		if (cur->defined == goal) {
			return cur;
		}
		
		cur = cur->link;
	}
	return NULL;
}

// Adds a node to the references for the given node
int addRef(long int *hex, char *addr, long int val) {
	node *nd;
	if (*hex == INVALID) { // symbol name
		nd = findSymbol(headPtr, addr);
	} else { // hex address
		nd = findAddr(headPtr, *hex);
	}
	// Make sure a node was found
	if (nd == NULL) {
		return FALSE;
	}
	
	// Create a new node
	node *n = (node *)malloc(sizeof(node));
	if (n == NULL) { // Allocation failed
		printf("Memory allocation failed");
		return FALSE;
	}
	
	if (nd->refs == NULL) { // Case of empty list
		// Assign the reference to the node to the head pointer
		n->value = val;
		nd->refs = n;
		
	} else { // Head is not null
		// Traverse to the end of the list
		node *cur = nd->refs;
		while (cur->link != NULL) {
            cur = cur->link;
        }
		
		// Assign the reference to the node and add it to the list
		n->value = val;
		
		cur->link = n; // Insert the new node into the list
	}
	return TRUE;
}