#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Christopher Lenk for COS 250
// Modified from code by Dr. Hunt
// Reverses the characters in the given string

void reverse_string(char *string) {
	// Find the end of the string
	char *end = string;
	while (*end != '\0') {
		end++;
	}
	end--;
	
	char *start = string;
	while (start < end) {
		// Switch the characters
		char temp = *end;
		*end = *start;
		*start = temp;
		
		start++;
		end--;
	}
	
    return;
}

main(){
   char *s1 = malloc(4);
   strcpy(s1,"XYZ");
   char *s2= malloc(5);
   strcpy(s2,"JURY");
   char *s3= malloc(7);
   strcpy(s3,"XRCQEF");

   printf("%s reverse is ", s1);
   reverse_string(s1);
   printf("%s\n", s1);

   printf("%s reverse is ", s2);
   reverse_string(s2);
   printf("%s\n", s2);

   printf("%s reverse is ", s3);
   reverse_string(s3);
   printf("%s\n", s3);
   
}
