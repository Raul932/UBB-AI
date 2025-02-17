bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    input dw 1223h, 5628h, 5629h
    lenInput equ ($-input) / 2
    dest times lenInput db 0
    lenDest db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, lenInput
        mov esi, 0
        mov edi, 0
        jecxz endloop
        repeta:
            mov al, [input+esi]
            mov bl, al
            cbw
            mov dl, 5
            idiv dl
            cmp ah, 0
            jne notdivisible
            mov [dest+edi], bl
            inc edi
            
            notdivisible:
            add esi, 2
            loop repeta
        endloop:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
