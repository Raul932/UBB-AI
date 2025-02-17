bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    A db 17, 4, 2, -2, -1
    
    A_length equ $-A
    
    B times A_length db 0
    


; our code starts here
segment code use32 class=code
    start:
        mov esi, 0
        mov edi, 0
        mov ecx, A_length
        jecxz end_loop
        repeta:
            mov al, [A+esi]
            inc esi
            cmp al, 0
            jl negative
            
            next:
                loop repeta
                jmp end_loop
            
            negative:
                mov [B+edi], al
                inc edi
                jmp next
            
 
        end_loop:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
