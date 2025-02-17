#include "Game.h"
#include "Pawn.h"
#include "Rook.h"
#include "ChessException.h"
#include <iostream>

int main() {
    Game chessGame(8, 8);

    Pawn pawn1(1, 2);
    Rook rook2(3, 4);

    chessGame.addPiece(&pawn1);

    chessGame.addPiece(&rook2);

    try {
        chessGame.movePiece(&pawn1, 1, 2);
    } catch (const ChessException& e) {
        std::cerr << e.what() << std::endl;
    }

    assert(pawn1.getX() == 1 && pawn1.getY() == 2);

    assert(rook2.getX() == 3 && rook2.getY() == 3);


    return 0;
}
