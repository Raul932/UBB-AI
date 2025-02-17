bits 32

global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
    
;16. (a+b)/2 + (10-a/c)+b/4 data types: a,b,c - byte, d - word and
;second expression: x/2+100*(a+b)-3/(c+d)+e*e; a,c-word; b,d-byte; e-doubleword; x-qword

    a db 2
    b db 3
    c db 5
    d dw 7
segment code use32 class=code
    start:
        mov eax, 0
        mov al, [a]
        
        push    dword 0
        call    [exit]
