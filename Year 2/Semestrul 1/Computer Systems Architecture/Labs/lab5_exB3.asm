bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit  
extern scanf, printf, gets
import scanf msvcrt.dll
import printf msvcrt.dll
import gets msvcrt.dll             ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s resb 20
    formats db '%s', 0
    mesaj db 's = ', 0
    d resb 20
    mesajfinal db 'd = ', 0
    newline db 10, 0

; our code starts here
segment code use32 class=code
    start:
        push dword mesaj
        call [printf]
        add esp, 4*1
        
        push dword s
        call [gets]
        add esp, 4*1
        
        mov edi, d
        mov esi, 0
        mov ecx, 0
        repeta:
            mov al, byte [esi+s]
            inc esi
            
            cmp al, 0
            je done
            
            cmp al, 'A'
            jl not_uppercase
            
            cmp al, 'Z'
            jg not_uppercase
            
            mov [edi + ecx], al
            inc ecx
        
        not_uppercase:
            jmp repeta
        
        done: 
            mov byte [edi + ecx], 0
            push dword newline
            call [printf]
            add esp, 4
            
            push dword mesajfinal
            call [printf]
            add esp, 4*1
            
            push dword d
            push dword formats
            call [printf]
            add esp, 4*2
            
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
