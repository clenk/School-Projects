#ifndef SALES_H
#define SALES_H
#define MAX_NAME 20
#define NUM_MONTHS 12
#define MAX_ENTRY 100
#define FALSE 0
#define TRUE 1

typedef struct EMP {
    char name[MAX_NAME+1];
    int monthSales[NUM_MONTHS];
    int total;
} Emp;
#endif

