#include "Pawn.h"
#include <stdexcept>

void Pawn::move(ChessBoard& board, int x, int y) {
    if (!isValidMove(x, y, board)) {
        throw std::runtime_error("Invalid move for Pawn!");
    }
    posX = x;
    posY = y;
}

bool Pawn::isValidMove(int newX, int newY, const ChessBoard& board) const {
    if (newX < 0 || newX >= board.getWidth() || newY < 0 || newY >= board.getHeight()) {
        return false;
    }

    if (newX == posX && newY == posY + 1) {
        return true;
    }

    if ((newX == posX + 1 || newX == posX - 1) && newY == posY + 1) {
        return true;
    }

    if (posY == 1 && newX == posX && newY == posY + 2) {
        return true;
    }

    return false;
}

