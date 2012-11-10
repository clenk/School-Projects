/* 
	Tests an implementation of a Linked List in C.
	Based on code in Pointers On C, by Kenneth A. Reek.
*/

#include <stdlib.h>
#include <stdio.h>

#define FALSE 0
#define TRUE 1

struct listNode {
	int value;
	struct listNode *link;
};

typedef struct listNode node;

// Initialize the list with a head node
node *head;
node **headPtr = &head;

// Adds a new node with the given value to the linked list
int add(node **linkp, int new_value) {
	node *current;
	node *new;
	
	// Look for the right place by traversing the list until we reach
	// a node with a value greater than or equal to the new value.
	while ((current = *linkp) != NULL && current->value < new_value) {
		linkp = &current->link;
	}
	
	// Allocate a new node and store the new value into it.
	new = (node *)malloc(sizeof(node));
	if (new == NULL) {
		return FALSE; // Allocation failed
	}
	new->value = new_value;
	
	// Insert the new node into the list and return true.
	new->link = current;
	*linkp = new;
	return TRUE;
}

// Deletes the node with the given value
void delete(node **linkp, int del_value) {
	if (linkp == NULL) { // Check for empty list
		printf("%s\n", "Can't delete from an empty list!");
		return;
	}

	node *cur;
	node *forward;
	cur = *linkp;
	forward = cur->link->link;
	
	// Find the correct node
	while (cur != NULL && cur->link->link != NULL && cur->link->value != del_value ) {
		if (cur->value == del_value) { // If the node was the first node
			headPtr = &(cur->link);
			return;
		}
		
		cur = cur->link;
		forward = forward->link;
	}
	
	if (cur->link->value == del_value && forward != NULL) { // If the node was found in the middle of the list
		cur->link = forward;
	} else if (cur->link->value == del_value) { // If the node was found at the end of the list
		cur->link = NULL;
	}
}

// Prints out the linked list, starting at the given node
void print(node **start) {
	node *cur;
	cur = *start;
	while (cur != NULL) {
		printf("%d\n", cur->value);
		cur = cur->link;
	}
}

// MAIN - Tests the linked list
void main(){
	
	// Add some nodes and print the resulting list
	add(headPtr, 90);
	add(headPtr, 22);
	add(headPtr, 44);
	add(headPtr, 33);
	add(headPtr, 55);
	add(headPtr, 8);
	add(headPtr, 99);
	add(headPtr, 76);
	add(headPtr, 19);
	add(headPtr, 89);
	add(headPtr, 2);
	print(headPtr);
	puts("");
	
	// Delete some nodes and print the resulting list
	delete(headPtr, 99);
	delete(headPtr, 2);
	delete(headPtr, 55);
	delete(headPtr, 9);
	print(headPtr);
}