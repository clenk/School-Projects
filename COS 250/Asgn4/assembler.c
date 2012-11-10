/* 
	Christopher Lenk for COS 250 - Spring 2012
*/

#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <time.h>
#include "linklist.h" // Handles the linked list

#define FALSE 0
#define TRUE 1
#define INVALID -1

#define LINE_LENGTH 100 // Max number of characters allowed on a single line in the data files
#define DEC 10
#define HEX 16

int errors = FALSE;

// Determines if the string passed in is a valid hexidecimal value and if it is, returns it
long int validHex(char *string) {
	char *p = string;
	while (*p != '\0') {
		int is_hex_char = (*p >= '0' && *p <= '9') ||
					      (*p >= 'a' && *p <= 'f') ||
                          (*p >= 'A' && *p <= 'F');
		if (!is_hex_char) {
			return INVALID;
		}
		p++;
	}
	return strtoul(string, NULL, HEX);
}
// Determine if the string passed in is a valid decimal value and if it is, returns it
long int validDec(char *string) {
	char *p = string;
	while (*p != '\0') {
		int is_dec_char = (*p >= '0' && *p <= '9');
		if (!is_dec_char) {
			return INVALID;
		}
		p++;
	}
	return strtoul(string, NULL, DEC);
}

// Prints out the hex representation of the instruction and its argument
void printInst(long int *hex, char *address, char *sufx, FILE *outf, FILE *bin) {
	if (*hex == INVALID) { // If the address given isn't hex, assume it's a symbol name
		node *n = findSymbol(headPtr, address);
		
		if (n == NULL) {
			errors = TRUE;
			fprintf(outf, "\nCould not find given symbol: %s!\n", address);
		} else {
			fprintf(outf, "%X", n->defined);
			unsigned short num = (unsigned short)strtoul(sufx, NULL, HEX);
			num += n->defined;
			fwrite(&num, sizeof(unsigned short), 1, bin);
		}
	} else {
		node *n = findAddr(headPtr, *hex);
			
		if (n == NULL) {
			errors = TRUE;
			fprintf(outf, "\nCould not find given address: %s!\n", address);
		} else {
			fprintf(outf, "%X", n->defined);
			unsigned short num = (unsigned short)strtoul(sufx, NULL, HEX);
			num += n->defined;
			fwrite(&num, sizeof(unsigned short), 1, bin);
		}
	}
}

// Handles the printing (to .lst and .bin) for the skipcond opcode
void printSkip(long int *hex, char *address, char *sufx, FILE *outf, FILE *bin) {
	if (strncmp("000", address, MAX_SYM_LEN) == 0 ||
		strncmp("400", address, MAX_SYM_LEN) == 0 ||
		strncmp("800", address, MAX_SYM_LEN) == 0 ) {
			fprintf(outf, "%s", address);
			unsigned short num = (unsigned short)strtoul(sufx, NULL, HEX);
			num += *hex;
			fwrite(&num, sizeof(unsigned short), 1, bin);
	} else {
		errors = TRUE;
		fprintf(outf, "\nSkipcond has incorrect value: %s!\n", address);
	}
}

// Parses symbols (and adds them to the symbol table linked list)
void symbol(char *alias, char *inst, char *addr, char *spc, long int *cur) {
	// Store Hex and Decimal versions of the address/value part of the instruction
	long int validHexi = -1;
	long int validDeci = -1;
	if (addr != NULL) {
		validHexi = validHex(addr);
		validDeci = validDec(addr);
	}
	
	// If there's a comma in the alias, remove it
	if (alias != NULL && strrchr(alias, ',') != NULL) {
		alias[strcspn(alias, ",")] = '\0';
		
	} else if (alias == NULL) { // Use a blank alias if none given
		alias = "        ";
	}
	
	// For known instructions...
	if (strncmp("jns", inst, MAX_SYM_LEN) == 0 ||
		strncmp("load", inst, MAX_SYM_LEN) == 0 ||
		strncmp("store", inst, MAX_SYM_LEN) == 0 ||
		strncmp("add", inst, MAX_SYM_LEN) == 0 ||
		strncmp("subt", inst, MAX_SYM_LEN) == 0 ||
		strncmp("input", inst, MAX_SYM_LEN) == 0 ||
		strncmp("output", inst, MAX_SYM_LEN) == 0 ||
		strncmp("halt", inst, MAX_SYM_LEN) == 0 ||
		strncmp("skipcond", inst, MAX_SYM_LEN) == 0 ||
		strncmp("jump", inst, MAX_SYM_LEN) == 0 ||
		strncmp("clear", inst, MAX_SYM_LEN) == 0 ||
		strncmp("addi", inst, MAX_SYM_LEN) == 0 ||
		strncmp("jumpi", inst, MAX_SYM_LEN) == 0 ||
		strncmp("loadi", inst, MAX_SYM_LEN) == 0 ||
		strncmp("storei", inst, MAX_SYM_LEN) == 0 ||
		strncmp("end", inst, MAX_SYM_LEN) == 0) {
			if (strncmp("        ", alias, MAX_SYM_LEN) != 0) { // Don't add it to the symbol table if there's no alias
				add(headPtr, alias, -1, *cur, 2);
			}
		
	// If it's not one of the known instructions...
	} else {
		
		// Store a decimal value
		if (strncmp("dec", inst, MAX_SYM_LEN) == 0 && validDeci != INVALID) {
			add(headPtr, alias, validDeci, *cur, 1);
		
		// Store a hex value
		} else if (strncmp("hex", inst, MAX_SYM_LEN) == 0 && validHexi != INVALID) {
			add(headPtr, alias, validHexi, *cur, 2);
		
		}
	}
	
	if (addr == NULL) {
		addr = "";
	}
	
	(*cur)++;
}

// Parses instructions
int instruct(char *alias, char *inst, char *addr, char *spc, long int *cur, FILE *out, FILE *bin) {
	
	fprintf(out, "%X | ", *cur);

	// Store Hex and Decimal versions of the address/value part of the instruction
	long int validHexi = -1;
	long int validDeci = -1;
	if (addr != NULL) {
		validHexi = validHex(addr);
		validDeci = validDec(addr);
	}
	
	// If there's a comma in the alias, remove it
	if (alias != NULL && strrchr(alias, ',') != NULL) {
		alias[strcspn(alias, ",")] = '\0';
		
	} else if (alias == NULL) { // Use a blank alias if none given
		alias = "        ";
	}
	
	// JNS
	if (strncmp("jns", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "0");
		char suffix[MAX_SYM_LEN] = "0000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// LOAD
	} else if (strncmp("load", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "1");
		char suffix[MAX_SYM_LEN] = "1000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// STORE
	} else if (strncmp("store", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "2");
		char suffix[MAX_SYM_LEN] = "2000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// ADD
	} else if (strncmp("add", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "3");
		char suffix[MAX_SYM_LEN] = "3000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// SUBT
	} else if (strncmp("subt", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "4");
		char suffix[MAX_SYM_LEN] = "4000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// INPUT
	} else if (strncmp("input", inst, MAX_SYM_LEN) == 0) {
		char suffix[MAX_SYM_LEN] = "5000";
		fprintf(out, suffix);
		unsigned short num = (unsigned short)strtoul(suffix, NULL, HEX);
		fwrite(&num, sizeof(unsigned short), 1, bin);
		
	// OUTPUT
	} else if (strncmp("output", inst, MAX_SYM_LEN) == 0) {
		char suffix[MAX_SYM_LEN] = "6000";
		fprintf(out, suffix);
		unsigned short num = (unsigned short)strtoul(suffix, NULL, HEX);
		fwrite(&num, sizeof(unsigned short), 1, bin);
		
	// HALT
	} else if (strncmp("halt", inst, MAX_SYM_LEN) == 0) {
		char suffix[MAX_SYM_LEN] = "7000";
		fprintf(out, suffix);
		unsigned short num = (unsigned short)strtoul(suffix, NULL, HEX);
		fwrite(&num, sizeof(unsigned short), 1, bin);
		
	// SKIPCOND
	} else if (strncmp("skipcond", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "8");
		char suffix[MAX_SYM_LEN] = "8000";
		printSkip(&validHexi, addr, suffix, out, bin);
		
	// JUMP
	} else if (strncmp("jump", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "9");
		char suffix[MAX_SYM_LEN] = "9000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// CLEAR
	} else if (strncmp("clear", inst, MAX_SYM_LEN) == 0) {
		char suffix[MAX_SYM_LEN] = "A000";
		fprintf(out, suffix);
		unsigned short num = (unsigned short)strtoul(suffix, NULL, HEX);
		fwrite(&num, sizeof(unsigned short), 1, bin);
		
	// ADDI
	} else if (strncmp("addi", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "B");
		char suffix[MAX_SYM_LEN] = "B000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// JUMPI
	} else if (strncmp("jumpi", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "C");
		char suffix[MAX_SYM_LEN] = "C000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// LOADI
	} else if (strncmp("loadi", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "D");
		char suffix[MAX_SYM_LEN] = "D000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// STOREI
	} else if (strncmp("storei", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "E");
		char suffix[MAX_SYM_LEN] = "E000";
		printInst(&validHexi, addr, suffix, out, bin);
		addRef(&validHexi, addr, *cur);
		
	// END
	} else if (strncmp("end", inst, MAX_SYM_LEN) == 0) {
		fprintf(out, "     |          | END");
		return INVALID;
		
	// If it's not one of the known instructions...
	} else {
		
		// Store a decimal value
		if (strncmp("dec", inst, MAX_SYM_LEN) == 0) {
			if (validDeci == INVALID) {
				puts("'dec' has an invalid value");
			} else {
				fprintf(out, "%04d", validDeci);
				unsigned short num = (unsigned short)validDeci;
				fwrite(&num, sizeof(unsigned short), 1, bin);
			}
			
		// Store a hex value
		} else if (strncmp("hex", inst, MAX_SYM_LEN) == 0) {
			if (validHexi == INVALID) {
				puts("'hex' has an invalid value");
			} else {
				fprintf(out, "%04X", validHexi);
				unsigned short num = (unsigned short)validHexi;
				fwrite(&num, sizeof(unsigned short), 1, bin);
			}
		
		} else { // Instruction not found
			errors = TRUE;
			fprintf(out, "\nError: Instruction not found!\n");
		}
	}
	
	if (addr == NULL) {
		addr = "";
	}
	fprintf(out, " | %8s | %s %s\n", alias, inst, addr);
	
	(*cur)++;
	return TRUE;
}

// Goes through the given file and builds the symbol table in a linked list
int buildTable(FILE *in, char *spacer) {
	int startOfFile = TRUE; // True until the first instruction is read
	long int curAddrSpace = 0x0; // Default to start addresses at
	
	// Loop through the lines in the file
	char *line = malloc(LINE_LENGTH * sizeof(char));
	while (!feof(in)) {
		if (fgets(line, LINE_LENGTH, in) != NULL) { // Get the next line
			// Get the different parts of the instruction
			char *wrd1 = NULL;
			char *wrd2 = NULL;
			char *wrd3 = NULL;
			
			if ((wrd1 = strtok(line, " \t\r\n")) != NULL) {
				if ((wrd2 = strtok(NULL, " \t\r\n")) != NULL) {
					wrd3 = strtok(NULL, " \t\r\n");
				}
			}
			
			if (wrd1 != NULL) {
				// ORG
				if (strncmp("org", wrd1, MAX_SYM_LEN) == 0) {
					// If 'org' is not the first instruction or doesn't have an argument just ignore it
					if (startOfFile && wrd2 != NULL) { // Make sure 'org' is the first instruction in the file
						curAddrSpace = strtoul(wrd2, NULL, 16);
						// if org's argument was invalid, curAddrSpace will just be 0
					}
				// END
				} else if (strncmp("end", wrd1, MAX_SYM_LEN) == 0) {
					break;
				} else {
					if (wrd3 != NULL ) { // We have a named symbol!
						symbol(wrd1, wrd2, wrd3, spacer, &curAddrSpace);
					} else { // Unnamed symbol
						symbol(NULL, wrd1, wrd2, spacer, &curAddrSpace);
					}
				}
				
				if (startOfFile) { // After the first line...
					startOfFile = FALSE;
				}
			}
		}
	}
	return TRUE;
}

// Prints the output .lst file (also adds references to symbol table)
int makeList(FILE *in, FILE *out, char *file, char *spacer, FILE *bin) {
	// Print the file heading
	fprintf(out, "Assembly listing for: %s.mas\n", file);
	time_t rawtime;
    struct tm * timeinfo;
    time (&rawtime);
    timeinfo = localtime (&rawtime);
	fprintf(out, "Assembled on: %s\n", asctime (timeinfo));
	
	int startOfFile = TRUE; // True until the first instruction is read
	long int curAddrSpace = 0x0; // Default to start addresses at
	
	// Loop through the lines in the file
	char *line = malloc(LINE_LENGTH * sizeof(char));
	while (!feof(in)) {
		if (fgets(line, LINE_LENGTH, in) != NULL) { // Get the next line
			// Get the different parts of the instruction
			char *word1 = NULL;
			char *word2 = NULL;
			char *word3 = NULL;
			
			if ((word1 = strtok(line, " \t\r\n")) != NULL) {
				if ((word2 = strtok(NULL, " \t\r\n")) != NULL) {
					word3 = strtok(NULL, " \t\r\n");
				}
			}
			
			if (word1 != NULL) {
				// ORG
				if (strncmp("org", word1, MAX_SYM_LEN) == 0) {
					
					if (word2 == NULL) { // Make sure a value was passed to it
						puts("Must give a value for 'org' instruction");
						return FALSE;
					} else {
						if (startOfFile) { // Make sure 'org' is the first instruction in the file
							curAddrSpace = strtoul(word2, NULL, 16);
							if (curAddrSpace == 0) {
								fprintf(out, "\n'org' has an invalid starting value!\n");
							} else {
								fprintf(out, "           | %s | ORG %s\n", spacer, word2);
							}
						} else {
							fprintf(out, "\n'org' instruction may only be the very first instruction in the file!\n");
						}
					}
				} else {
					if (word3 != NULL ) { // We have a named symbol!
						if (instruct(word1, word2, word3, spacer, &curAddrSpace, out, bin) == INVALID) break;
					} else { // Unnamed symbol
						if (instruct(NULL, word1, word2, spacer, &curAddrSpace, out, bin) == INVALID) break;
					}
				}
			
			}
		
			if (startOfFile) { // After the first line...
				startOfFile = FALSE;
			}
		}
	}
	
	return TRUE;
}

// MAIN
int main(int argc, char *argv[]) {
	// Error-check the command-line input
	if (argc != 2) {
		puts("Error: Must have exactly 1 commandline argument: the source file to be assembled");
		return FALSE;
	}
	
	// Open the file streams
	FILE *infile;
	FILE *outlist;
	FILE *outbin;
	
	// Make sure the input file opens okay
	if ((infile = fopen(argv[1], "r")) == NULL) {
		puts("Error: Specified file could not be opened.");
		return FALSE;
	}
	
	// Open the output stream
	char *filename = strtok(argv[1], ".");
	char *filelst = malloc(LINE_LENGTH * sizeof(char));
	char *filebin = malloc(LINE_LENGTH * sizeof(char));
	strncpy(filelst, filename, LINE_LENGTH);
	strncat(filelst, ".lst", LINE_LENGTH);
	outlist = fopen(filelst, "w");
	strncpy(filebin, filename, LINE_LENGTH);
	strncat(filebin, ".bin", LINE_LENGTH);
	outbin = fopen(filebin, "w");
	
	// Create a blank spacer string for formating output
	char *spacer = malloc(MAX_SYM_LEN * sizeof(char));
	strncpy(spacer, " ", MAX_SYM_LEN);
	int i;
	for (i = 2; i < MAX_SYM_LEN; i++) {
		strncat(spacer, " ", MAX_SYM_LEN);
	}
	
	// Build the symbol table
	buildTable(infile, spacer);
	
	// Print the output listing to a file
	rewind(infile);
	makeList(infile, outlist, filename, spacer, outbin);
	
	// Output the symbol table
	print(headPtr, outlist);
	
	fclose(infile);
	fclose(outlist);
	fclose(outbin);
	
	// If an error is found do not produce a binary file
	if (errors) {
		remove(filebin);
	}
	
	return TRUE;
}