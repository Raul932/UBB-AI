bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit
extern scanf, printf, gets, getchar
import scanf msvcrt.dll
import printf msvcrt.dll
import gets msvcrt.dll             ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import getchar msvcrt.dll       ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s resb 20
    formats db '%s', 0
    caracter dd 0
    formatc db '%c', 0
    formatreads db 's=', 0
    formatreadc db 'c=', 0
    newline db 10, 0
; our code starts here
segment code use32 class=code
    start:
        push dword formatreads
        call [printf]
        add esp, 4*1
        
        push dword s
        call [gets]
        add esp, 4*1
        
        push dword formatreadc
        call [printf]
        add esp, 4*1
        
        push dword caracter
        push dword formatc
        call [scanf]
        add esp, 4*2
        
        call [getchar]
        
        push dword newline
        call [printf]
        add esp, 4*1
        
        mov edi, s
        mov al, [caracter]
        repeta:
            mov dl, [edi]
            cmp dl, 0
            je done
            
            cmp dl, al
            jne next_char
            
            mov byte [edi], 'X'
            
        next_char:
            inc edi
            jmp repeta
        
        done:
            push dword s
            push dword formats 
            call [printf]
            add esp, 4*2
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
