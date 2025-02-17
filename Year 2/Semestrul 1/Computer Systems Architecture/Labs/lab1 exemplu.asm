bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 2
    b db 11
    c dw 8
    r dd 0

; our code starts here
segment code use32 class=code
    start:
        ;12+b
        mov eax, 0
        mov al, [b]
        add eax, 12
        ;2+c
        mov ebx, 0
        mov bx, [c]
        add ebx, 2
        ;12+b - (2+c)
        sub eax, ebx
        ;12+b - (2+c) - a
        mov ecx, 0  
        mov cx, [a]
        sub eax, ecx
        mov edx, 0
        mov dl, [b]
        ;(12+b - (2+c) - a) - b
        sub eax, edx
        add eax, 1
        mov [r], eax
        
        
       
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
