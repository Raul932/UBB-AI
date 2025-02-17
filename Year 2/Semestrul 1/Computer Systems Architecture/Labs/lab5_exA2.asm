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
    x dd 0
    y dd 0
    z dd 0
    readformat db '%d', 0
    printformat db 'First number is: %d Second number is: %d Third number is: %d. The result of (%d+%d+%d)/3 is %x hexa', 0

; our code starts here
segment code use32 class=code
    start:
        push dword x
        push dword readformat
        call [scanf]
        add esp, 4*2
        
        push dword y
        push dword readformat
        call [scanf]
        add esp, 4*2
        
        push dword z
        push dword readformat
        call [scanf]
        add esp, 4*2
        
        mov eax, [x]
        mov edx, 0
        mov ebx, 3
        add eax, [y]
        add eax, [z]
        cdq
        idiv ebx
        
        push eax
        push dword [z]
        push dword [y]
        push dword [x]
        push dword [z]
        push dword [y]
        push dword [x]
        push dword printformat
        call [printf]
        add esp, 4*8
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
