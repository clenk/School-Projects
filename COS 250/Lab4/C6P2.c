#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Christopher Lenk for COS 250
// Modified from code by Dr. Hunt
// Deletes a specified portion from a string

int del_substr(char  *str, char  *substr) {
	int contained = 0; // Boolean
	
	// Find the length of the substring
	char *lencur = substr;
	int length = 0;
	while (*lencur != '\0') {
		lencur++;
		length++;
	}
	//printf("%d", length);
	
	// Pointers to the start and end of the substring in the main string
	char *start = NULL;
	char *end = NULL;
	
	int length2;
	char *subcur = substr;
	while (*subcur != '\0') { // Go through the substring
		char *strcur = str;
		while (*strcur != '\0') { // Go through the main string
			length2 = 0;
			while (*strcur == *subcur) { 
				// Keep track of the starting place of the substring in the main string
				if (start == NULL) {
					start = strcur;
				}
				
				// Increment variables
				strcur++;
				subcur++;
				length2++;
			}
			//printf("%d", length2);
			if (length == length2) { // Found the substring!
				end = strcur;
			}
			strcur++;
		}
		subcur++;
	}
	if (end == NULL) { // The substring is NOT contained in the string
		return 0;
	} else { // The substring IS contained in the string
		char *done = end; // Keep track of the end of the substring so we know how far to go
		while (start <= done+1) { // Go one farther than 'done' to get the terminating character
			*start = *end; // Overwrite the current letter
			
			// Increment
			start++;
			end++;
		}
		return 1;
	}
}

main(){
   char *str = malloc(8);
   strcpy(str,"ABCDEFG");
   char *s1 = malloc(4);
   strcpy(s1,"FGH");
   char *s2 = malloc(4);
   strcpy(s2,"CDF");
   char *s3 = malloc(5);
   strcpy(s3,"XABC");
   char *s4 = malloc(4);
   strcpy(s4,"CDE");
   int result;

   if((result = del_substr(str, s1)) == 0) {
       printf("%s Returned 0 %s\n", s1,str);
   } else {
       printf("%s Returned 1 %s\n", s1,str);
   }

   if((result = del_substr(str, s2)) == 0) {
       printf("%s Returned 0 %s\n", s2,str);
   } else {
       printf("%s Returned 1 %s\n", s2,str);
   }

   if((result = del_substr(str, s3)) == 0) {
       printf("%s Returned 0 %s\n", s3,str);
   } else {
       printf("%s Returned 1 %s\n", s3,str);
   }

   if((result = del_substr(str, s4)) == 0) {
       printf("%s Returned 0 %s\n", s4,str);
   } else {
       printf("%s Returned 1 %s\n", s4,str);
   }
}
