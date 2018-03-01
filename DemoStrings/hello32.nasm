BITS 32

;
; 32-bit hello
;
; http://blog.markloiseau.com/2012/06/64-bit-linux-shellcode/
;

; set to 1 for output without nulls
%define NONULLS 1

%if NONULLS

GLOBAL _start

_start:
	jmp do_call		; jump down and do a call to get the address

jmp_back:
	xor eax, eax	; zero registers
	xor ebx, ebx
	xor ecx, ecx
	xor edx, edx
	
	add eax, 4		; 4 is syscall for write
	add ebx, 1		; 1=fd for stdout
	pop ecx			; pop the message address
	add edx, length	; string length
	int 0x80
	
	xor eax, eax
	add eax, 1		; 1 is syscall for exit
	xor ebx, ebx	; 0 is the return code
	int 0x80
	
do_call:
	call jmp_back	; push the address of message onto the stack

message:
	db 'Hello, world!',0x0A

length:	equ	$-message

%else

GLOBAL _start

_start:
	jmp do_call		; jump down and do a call to get the address

jmp_back:
	
	mov eax, 4		; 4 is syscall for write
	mov ebx, 1		; 1=fd for stdout
	pop ecx			; pop the message address
	mov edx, length	; string length
	int 0x80
	
	mov eax, 1		; 1 is syscall for exit
	mov ebx, 0	; 0 is the return code
	int 0x80
	
do_call:
	call jmp_back	; push the address of message onto the stack

message:
	db 'Hello, world!',0x0A

length:	equ	$-message

%endif
