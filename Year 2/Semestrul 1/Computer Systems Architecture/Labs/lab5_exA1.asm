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
    a dd 0
    k dd 0
    readformat db '%x', 0
    readformatconst db '%d', 0
    printformat db 'The number is %d, The result of %d * %d is %d decimal, %x hexa', 0
    printmsga db 'a=', 0
    printmsgk db 'k=', 0

; our code starts here
segment code use32 class=code
    start:
        push dword printmsga
        call [printf]
        add esp, 4*1
        
        push dword a
        push dword readformat
        call [scanf]
        add esp, 4*2
        
        push dword printmsgk
        call [printf]
        add esp, 4*1
        
        push dword k
        push dword readformatconst
        call [scanf]
        add esp, 4*2
        
        mov eax, [a]
        imul dword [k]
        
        push eax
        push eax
        push dword [k]
        push dword [a]
        push dword [a]
        push dword printformat
        call [printf]
        add esp, 4*6
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
