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
    formats db '%s', 0
    readformat db 's=', 0
    rezultat db 'd=', 0
    newline db 10, 0

; our code starts here
segment code use32 class=code
    start:
        push dword readformat
        call [printf]
        add esp, 4*1
        
        push dword s 
        call [gets]
        add esp, 4*1
        
        mov esi, 0
        mov ecx, 0
        mov bl, byte 'S'
        repeta:
            mov al, byte [esi + s]
            
            cmp al, 0
            je done
            
            cmp al, 'A'
            jl not_upper
            
            cmp al, 'Z'
            jg not_upper
            
            mov [esi + s], bl 
            
        not_upper:
            inc esi 
            jmp repeta 
        done:
            mov byte [esi + s], 0
            push dword newline
            call [printf]
            add esp, 4*1
            
            push dword rezultat
            call [printf]
            add esp, 4*1
            
            push dword s 
            push dword formats 
            call [printf]
            add esp, 4*2
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
