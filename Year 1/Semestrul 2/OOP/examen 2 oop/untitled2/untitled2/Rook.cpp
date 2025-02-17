#include "Rook.h"
#include <stdexcept>

void Rook::move(ChessBoard& board, int x, int y) {
    if (!isValidMove(x, y, board)) {
        throw std::runtime_error("Invalid move for Rook!");
    }
    posX = x;
    posY = y;
}

bool Rook::isValidMove(int newX, int newY, const ChessBoard& board) const {
    return (newX == posX || newY == posY);
}
