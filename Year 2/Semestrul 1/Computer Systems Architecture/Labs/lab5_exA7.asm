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
    b dd 0
    formatdecimal db '%d', 0
    printresult db '%d mod %d = %d decimal, %x hexa', 0
    printmsga db 'a=', 0
    printmsgb db 'b=', 0

; our code starts here
segment code use32 class=code
    start:
        push dword printmsga
        call [printf]
        add esp, 4*1
        
        push dword a
        push dword formatdecimal
        call [scanf]
        add esp, 4*2
        
        push dword printmsgb
        call [printf]
        add esp, 4*1
        
        push dword b
        push dword formatdecimal
        call [scanf]
        add esp, 4*2
        
        mov eax, [a]
        mov edx, 0
        cdq
        idiv dword [b]
        
        push edx
        push edx
        push dword [b]
        push dword [a]
        push dword printresult
        call [printf]
        add esp, 4*5
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
