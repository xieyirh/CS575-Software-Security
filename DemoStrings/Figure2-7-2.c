
#include <string.h>
#include <stdio.h>

int main(int argc, char* argv[]) {
	char a[16];
	char b[16];
	char c[32];

	strncpy(a, "0123456789abcdef", sizeof(a));
	strncpy(b, "0123456789abcdef", sizeof(b));
	strncpy(c, a, sizeof(c));
	printf("a = %s\n", a);
	printf("b = %s\n", b);
	printf("c = %s\n", c);
	return 0;
}
