#include <stdio.h>
#include <string.h>

int checkPassword(void) {
	char password[16];
    bzero(password, 16);
	printf("Enter password: ");
	gets(password);
	if (strcmp(password, "opensesame")==0)
		return 1;
	else
	    return 0;
}

void openVault(){
	printf("Vault opened!\n");
}

int main(void) {
	if (checkPassword()) {
		openVault();
	} else {
		printf("Access denied!\n");
	}
}
