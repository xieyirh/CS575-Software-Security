#include <stdio.h>

int main(void) {
	char Password[80];
	puts("Enter 8 character password:");
	gets(Password);
	printf("password = %s\n", Password);
}
