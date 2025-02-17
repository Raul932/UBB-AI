bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 0111011101010111b
    b dw 1001101110111110b
    c dd 0
; our code starts here
segment code use32 class=code
    start:  
        mov eax, 0
        mov ebx, 0
        mov ecx, 0
        mov edx, 0
        movsx eax, word [a]
        movsx ebx, word [b]
        ;the bits 0-2 of C are the same as the bits 12-14 of A
        mov edx, eax
        shr edx, 12
        and edx, 0111b
        or ecx, edx
        ;the bits 3-8 of C are the same as the bits 0-5 of B
        mov edx, ebx
        and edx, 0111111b
        shl edx, 3
        or ecx, edx
        ;the bits 9-15 of C are the same as the bits 3-9 of A
        mov edx, eax
        shr edx, 3
        and edx, 01111111b
        shl edx, 9
        or ecx, edx
        ;the bits 16-31 of C are the same as the bits of A
        mov edx, eax
        and edx, 01111111111111111b
        shl edx, 16
        or ecx, edx
        mov [c], ecx
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
