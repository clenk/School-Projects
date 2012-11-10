#include <stdio.h>

// Christopher Lenk for COS 250
// Modified from code by Dr. Hunt
// Searches a string for any one of a given set of characters

char *find_char(char  *source, char  *chars) {
	if (chars == '\0') { // Return null if given string is empty
		return NULL;
	}

	char *cur1 = chars;
	
	// Loop through the source string
	while (*cur1 != '\0') {
		char *cur2 = source;
		
		// Loop through the other string
		while (*cur2 != '\0') {
			// If the characters are equal, we're done!
			if (*cur1 == *cur2) {
				return cur1;
			}
			cur2++;
		}		
		cur1++;
	}
	
	// If we make it this far, the none of the characters were found
	return NULL;
}

main(){
   char *source = "ABCDEFG";
   char *s1 = "XYZ";
   char *s2 = "JURY";
   char *s3 = "QQQQ";
   char *s4 = "XRCQEF";
   char *result;

   if((result = find_char(source, s1)) == NULL) {
       printf("Given %s %s Returned NULL\n", source, s1);
   } else {
       printf("Given %s %s Returned %c\n", source, s1, *result);
   }

   if((result = find_char(source, s2)) == NULL) {
       printf("Given %s %s Returned NULL\n", source, s2);
   } else {
       printf("Given %s %s Returned %c\n", source, s2, *result);
   }

   if((result = find_char(source, s3)) == NULL) {
       printf("Given %s %s Returned NULL\n", source, s3);
   } else {
       printf("Given %s %s Returned %c\n", source, s3, *result);
   }

   if((result = find_char(source, s4)) == NULL) {
       printf("Given %s %s Returned NULL\n", source, s4);
   } else {
       printf("Given %s %s Returned %c\n", source, s4, *result);
   }
}
