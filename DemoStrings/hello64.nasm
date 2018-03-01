BITS 64
;
; 64-bit "Hello World" shellcode
;
; http://blog.markloiseau.com/2012/06/64-bit-linux-shellcode/
;

; set to 1 for output without nulls
%define NONULLS 1

%if NONULLS
GLOBAL _start

_start:
	jmp get_address		; jump down and do a call to get the address

run:
	xor rax, rax	; zero the registers
	xor rdi, rdi
	xor rdx, rdx
	
	; sys_write(stdout, message, length)
	add	rax, 1		; sys_write
	add	rdi, 1		; stdout
	pop rsi			; message address
	add	rdx, 14		; message string length
	syscall
	
	; sys_exit(return_code)
	xor rax, rax
	add	rax, 60		; sys_exit
	xor rdi, rdi	; return 0 (success)
	syscall
	
get_address:
	call run		; put address of message on top of the stack

message:
	db 'Hello, world!',0x0A

;length:	equ	$-message

%else
; produces nulls. not good for shellcode

GLOBAL _start

_start:
	jmp get_address		; jump down and do a call to get the address

run:

	; sys_write(stdout, message, length)
	mov	rax, 1		; sys_write
	mov	rdi, 1		; stdout
	pop rsi			; pop the message address off the stack
	mov	rdx, 14		; message string length
	syscall
	
	; sys_exit(return_code)
	mov	rax, 60		; sys_exit
	mov	rdi, 0		; return 0 (success)
	syscall
	
get_address:
	call run	; push address of message onto the stack

message:
	db 'Hello, world!',0x0A

%endif
