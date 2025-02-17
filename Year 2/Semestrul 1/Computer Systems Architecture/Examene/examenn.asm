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
import getchar msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s resb 30
    c1 dd 0
    formread db 's=', 0
    formreadc db 'c=', 0
    formatc db '%c', 0
    formats db '%s', 0
    afisare db 'number= %d', 0
; our code starts here
segment code use32 class=code
    start:
        push dword formread
        call [printf]
        add esp, 4
        
        push dword s
        call [gets]
        add esp, 4
        
        push dword formreadc 
        call [printf]
        add esp, 4
        
        push dword c1 
        push dword formatc
        call [scanf]
        add esp, 4*2
        
        mov esi, 0
        mov al, byte [c1]
        mov ecx, 0
        repeta:
            mov bl, byte [esi+s]
            inc esi
            cmp bl, 0
            je done
            
            cmp al, bl 
            jne not_occ
            
            inc ecx
        not_occ:
            jmp repeta
        done:
             push dword ecx 
             push dword afisare
             call [printf]
             add esp, 4*2
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
