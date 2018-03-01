BITS 64
;
; 64-bit "Hello World" shellcode
;
; http://blog.markloiseau.com/2012/06/64-bit-linux-shellcode/
;

GLOBAL _start

_start:
	jmp print
	
message:
	db 'Hello, world!',0x0A
	
print:
	xor rax, rax	; zero the registers
	xor rdi, rdi
	xor rdx, rdx
	
	; sys_write(stdout, message, length)
	add	rax, 1		; sys_write
	add	rdi, 1		; stdout
	lea rsi, [rel message]	; message address
	add	rdx, 14		; message string length
	syscall
	
	; sys_exit(return_code)
	xor rax, rax
	add	rax, 60		; sys_exit
	xor rdi, rdi	; return 0 (success)
	syscall


